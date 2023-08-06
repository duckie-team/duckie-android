/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.user.repository.UserRepository
import javax.inject.Inject

@Immutable
class FetchIgnoreUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke() = runCatching {
        userRepository.fetchIgnoreUsers().toImmutableList()
    }
}
