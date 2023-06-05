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
import io.ktor.client.request.post
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
import team.duckie.app.android.data.user.model.UserProfileData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.data.user.model.UsersResponse
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.ExperimentalApi
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.runtimeCheck
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val fuel: Fuel,
) : UserDataSource {
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
        introduction: String?,
        status: String?,
    ): User {
        runtimeCheck(
            nickname != null || profileImageUrl != null || categories != null ||
                    tags != null || introduction != null || status != null,
        ) {
            "At least one of the parameters must be non-null"
        }

        val response = client.patch("/users/$id") {
            jsonBody {
                categories?.let { "favoriteCategories" withInts categories.fastMap { it.id } }
                tags?.let { "favoriteTagIds" withInts tags.fastMap { it.id } }
                profileImageUrl?.let { "profileImageUrl" withString profileImageUrl }
                nickname?.let { "nickName" withString nickname }
                introduction?.let { "introduction" withString introduction }
                status?.let { "status" withString status }
            }
        }
        return responseCatching(
            response = response.body(),
            parse = UserResponse::toDomain,
        )
    }

    override suspend fun nicknameValidateCheck(nickname: String): Boolean {
        val response = client.post("/users/duplicate-check") {
            jsonBody {
                "nickName" withString nickname
            }
        }

        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    @AllowMagicNumber
    @ExperimentalApi
    override suspend fun fetchRecommendUserFollowing(userId: Int): UserFollowings =
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

    override suspend fun fetchUserProfile(userId: Int): UserProfile {
        val response = client.get("/profile/$userId")
        return responseCatching(
            response = response.body(),
            parse = UserProfileData::toDomain,
        )
    }

    override suspend fun fetchUserFollowings(userId: Int): List<User> {
        val response = client.get("/users/$userId/followings")
        return responseCatching(
            response = response.body(),
            parse = UsersResponse::toDomain,
        )
    }

    override suspend fun fetchUserFollowers(userId: Int): List<User> {
        val response = client.get("/users/$userId/followers")
        return responseCatching(
            response = response.body(),
            parse = UsersResponse::toDomain,
        )
    }
}
