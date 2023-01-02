/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.repository

import io.ktor.client.call.body
import io.ktor.client.request.patch
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.repository.UserRepository
import team.duckie.app.android.util.kotlin.runtimeCheck

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun update(
        id: Int,
        nickname: String?,
        profileImageUrl: String?,
        favoriteCategories: List<Category>?,
        favoriteTags: List<Tag>?,
    ): User {
        runtimeCheck(
            nickname != null || profileImageUrl != null ||
                    favoriteCategories != null || favoriteTags != null
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
}
