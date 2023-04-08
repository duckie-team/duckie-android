/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import team.duckie.app.android.domain.user.repository.UserRepository
import javax.inject.Inject

class FetchUserProfileUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(userId: Int) = runCatching {
        repository.fetchUserProfile(userId)
    }
}
