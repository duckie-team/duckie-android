/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.heart.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.heart.repository.HeartRepository

@Immutable
class DeleteHeartUseCase @Inject constructor(
    private val heartRepository: HeartRepository,
) {
    suspend operator fun invoke(heartId: Int) = runCatching {
        heartRepository.deleteHeart(heartId)
    }
}
