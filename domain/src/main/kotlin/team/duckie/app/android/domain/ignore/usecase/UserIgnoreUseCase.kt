/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.ignore.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.ignore.repository.IgnoreRepository
import javax.inject.Inject

@Immutable
class UserIgnoreUseCase @Inject constructor(
    private val ignoreRepository: IgnoreRepository,
) {

     suspend operator fun invoke(targetId: Int) = runCatching {
         ignoreRepository.ignoreUser(targetId)
     }
}
