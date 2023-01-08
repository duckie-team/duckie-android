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

@Immutable
interface UserRepository {
    suspend fun get(id: Int): User

    suspend fun update(
        id: Int,
        nickname: String?,
        profileImageUrl: String?,
        favoriteCategories: List<Category>?,
        favoriteTags: List<Tag>?,
    ): User

    suspend fun nicknameValidateCheck(nickname: String): Boolean
}
