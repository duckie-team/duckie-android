/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.repository

import android.service.autofill.UserData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.toJsonObject
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserFollowingData
import team.duckie.app.android.data.user.model.UserFollowingRecommendationsData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.repository.UserRepository
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.runtimeCheck

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun get(id: Int): User {
        val response = client.get("/users/$id")

        return responseCatching(
            response = response.body(),
            parse = UserResponse::toDomain,
        )
    }

    override suspend fun update(
        id: Int,
        nickname: String?,
        profileImageUrl: String?,
        favoriteCategories: List<Category>?,
        favoriteTags: List<Tag>?,
    ): User {
        runtimeCheck(
            nickname != null || profileImageUrl != null ||
                    favoriteCategories != null || favoriteTags != null,
        ) {
            "At least one of the parameters must be non-null"
        }
        val response = client.patch("/users/$id") {
            jsonBody {
                nickname?.let { "nickname" withString nickname }
                profileImageUrl?.let { "profileImageUrl" withString profileImageUrl }
                favoriteCategories?.let { "favoriteCategories" withPojos favoriteCategories }
                favoriteTags?.let { "favoriteTags" withPojos favoriteTags }
            }
        }
        return responseCatching(
            response = response.body(),
            parse = UserResponse::toDomain,
        )
    }

    override suspend fun nicknameValidateCheck(nickname: String): Boolean {
        val response = client.get("/users/$nickname/duplicate-check")

        return responseCatching(response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    @ExperimentalApi
    override suspend fun getUserFollowing(): UserFollowing {
//        val response = client.get {
//            url("/users/following")
//        }
//
//        return responseCatching(response.bodyAsText()) { body ->
//            body.toJsonObject<List<UserResponse>>().fastMap(UserResponse::toDomain)
//        }
//      TODO(limsaehyun): Server Request 필요

        val mockFollowingResponse = UserFollowingData(
            followingRecommendations = listOf(
                UserFollowingRecommendationsData(
                    category = CategoryData(
                        id = 1,
                        name = "닉네임",
                        thumbnailUrl =  "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                        popularTags = listOf(
                            TagData(
                                id = 1,
                                name = "Tag1"
                            ),
                            TagData(
                                id = 1,
                                name = "Tag1"
                            ),
                            TagData(
                                id = 1,
                                name = "Tag1"
                            )
                        )
                    ),
                    user = (0..2).map {
                        UserResponse(
                            tier = 1,
                            nickName = "닉네임",
                            id = 1,
                            profileImageUrl = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                            favoriteTags = (0..2).map {
                                Tag(
                                    id = 1,
                                    name = "Tag1"
                                )
                            },
                            favoriteCategories = listOf(
                                Category(
                                    id = 1,
                                    name = "User",
                                    thumbnailUrl = "",
                                    popularTags = persistentListOf(),
                                )
                            )
                        )
                    }.toList()
                )
            )
        )

        return mockFollowingResponse.toDomain()
    }
}
