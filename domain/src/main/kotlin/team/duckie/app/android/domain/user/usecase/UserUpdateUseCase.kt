/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.repository.UserRepository

@Immutable
class UserUpdateUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(
        id: Int,
        categories: List<Category>?,
        tags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
        status: String?,
        updateMeInstance: (me: User) -> Unit = {},
    ): Result<User> {
        return runCatching {
            repository.update(
                id = id,
                categories = categories,
                tags = tags,
                profileImageUrl = profileImageUrl,
                nickname = nickname,
                status = status,
            )
        }.also { result ->
            result.getOrNull()?.let { user ->
                updateMeInstance(user)
            }
        }
    }
}
