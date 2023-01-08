/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.repository.UserRepository

@Immutable
class GetUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(id: Int): Result<User> {
        return runCatching { repository.get(id) }
    }
}
