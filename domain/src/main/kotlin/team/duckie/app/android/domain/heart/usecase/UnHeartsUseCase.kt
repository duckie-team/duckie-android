/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.heart.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.heart.model.HeartsBody
import team.duckie.app.android.domain.heart.repository.HeartsRepository

@Immutable
class UnHeartsUseCase @Inject constructor(
    private val heartsRepository: HeartsRepository,
) {
    suspend operator fun invoke(heartsBody: HeartsBody) = runCatching {
        heartsRepository.unHeart(heartsBody)
    }
}
