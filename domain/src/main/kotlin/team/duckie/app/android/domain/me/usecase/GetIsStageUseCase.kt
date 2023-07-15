/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.me.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.me.MeRepository
import javax.inject.Inject

// TODO(riflockle7): 추후 GetMeUsecase 와 기능 합치기
@Immutable
class GetIsStageUseCase @Inject constructor(
    private val repository: MeRepository,
) {
    suspend operator fun invoke(): Result<Boolean> {
        return runCatching { repository.getIsStage() }
    }
}
