/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings

@Immutable
interface UserRepository {
    suspend fun get(id: Int): User

    suspend fun update(
        id: Int,
        categories: List<Category>?,
        tags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
        status: String?,
    ): User

    suspend fun nicknameValidateCheck(nickname: String): Boolean

    suspend fun fetchUserFollowing(userId: Int): UserFollowings

    suspend fun fetchMeFollowers(): List<User>

    suspend fun fetchMeFollowings(): List<User>
}
