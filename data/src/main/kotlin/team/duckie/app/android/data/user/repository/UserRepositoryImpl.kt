/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.repository

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
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings
import team.duckie.app.android.domain.user.repository.UserRepository
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.runtimeCheck
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val fuel: Fuel) : UserRepository {
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
