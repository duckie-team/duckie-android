/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.datasource

import com.github.kittinunf.fuel.Fuel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserFollowingsResponse
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.data.user.model.UsersResponse
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.datasource.UserDataSource
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.ClientMeIdNull
import team.duckie.app.android.util.kotlin.ClientMeTokenNull
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.duckieClientLogicProblemException
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.runtimeCheck
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val fuel: Fuel,
) : UserDataSource {
    // TODO(riflockle7): MeRepository, MeDataSource 만들면서 없어질 예정
    override suspend fun getMe(): User? {
        // 1. 토큰 값이 등록되어 있는지 먼저 확인한다 (이게 없으면 유저 정보 가져오는 거 자체가 안됨)
        val meToken = getMeToken()

        // TODO 토큰이 없다면, 로그인 화면으로 보내주어야 한다 (해결책 생각하면 meToken 선언부로 처리 옮길 예정)
        meToken ?: duckieClientLogicProblemException(code = ClientMeTokenNull)

        // 2. 토큰이 있다면 토큰 검증한다.
        val accessTokenValid = authDataSource.checkAccessToken(meToken).userId > 0

        if (accessTokenValid) {
            // 3. id 값이 등록되어 있는지 확인한다.
            val meId = getMeId()

            // TODO id 가 없다면, 로그인 화면으로 보내주어야 한다 (해결책 생각하면 meId 선언부로 처리 옮길 예정)
            meId ?: duckieClientLogicProblemException(code = ClientMeIdNull)

            // 4. me 객체값이 있는지 확인한다
            return me ?: kotlin.run {
                // 5. accessToken 관련 설정
                authDataSource.attachAccessTokenToHeader(meToken)

                // 6. me 객체가 없다면, id 기반으로 유저 정보를 가져온 후 setMe 를 통해 설정 뒤 반환한다.
                val user = get(meId)
                setMe(user)

                return user
            }
        } else {
            // TODO token 문제가 있다면, 로그인 화면으로 보내주어야 한다 (해결책 생각하면 아래 코드 수정 예정)
            return me
        }
    }

    // TODO(riflockle7): MeRepository, MeDataSource 만들면서 없어질 예정
    override suspend fun setMe(newMe: User) {}

    override suspend fun get(id: Int): User {
        val response = client.get("/users/$id")

        return responseCatching(
            response = response.body(),
            parse = UserResponse::toDomain,
        )
    }

    override suspend fun update(
        id: Int,
        categories: List<Category>?,
        tags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
        status: String?,
    ): User {
        runtimeCheck(
            nickname != null || profileImageUrl != null || categories != null ||
                    tags != null || status != null,
        ) {
            "At least one of the parameters must be non-null"
        }

        val response = client.patch("/users/$id") {
            jsonBody {
                categories?.let { "favoriteCategories" withInts categories.fastMap { it.id } }
                tags?.let { "favoriteTags" withInts tags.fastMap { it.id } }
                profileImageUrl?.let { "profileImageUrl" withString profileImageUrl }
                nickname?.let { "nickname" withString nickname }
                status?.let { "status" withString status }
            }
        }
        return responseCatching(
            response = response.body(),
            parse = UserResponse::toDomain,
        )
    }

    override suspend fun nicknameValidateCheck(nickname: String): Boolean {
        val response = client.get("/users/$nickname/duplicate-check")

        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    // TODO(riflockle7): GET /users/following API commit (변경점이 많아 TODO 적음)
    @AllowMagicNumber
    @ExperimentalApi
    override suspend fun fetchUserFollowing(userId: Int): UserFollowings =
        withContext(Dispatchers.IO) {
            val (_, response) = fuel
                .get(
                    "/users/following",
                )
                .responseString()

            return@withContext responseCatchingFuel(
                response = response,
                parse = UserFollowingsResponse::toDomain,
            )
        }

    override suspend fun fetchMeFollowers(): List<User> {
        val (_, response) = fuel
            .get("/users/me/followers")
            .responseString()

        return responseCatchingFuel(
            response = response,
            parse = UsersResponse::toDomain,
        )
    }

    override suspend fun fetchMeFollowings(): List<User> {
        val (_, response) = fuel
            .get("/users/me/followings")
            .responseString()

        return responseCatchingFuel(
            response = response,
            parse = UsersResponse::toDomain,
        )
    }
}
