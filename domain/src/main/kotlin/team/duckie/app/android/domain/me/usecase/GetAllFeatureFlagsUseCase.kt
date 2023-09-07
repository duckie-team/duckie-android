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

// TODO(riflockle7): 그냥 모든 피처 플래그를 Map<String, Boolean> 형태로 받아내는 건 어떨까?
@Immutable
class GetIsProceedEnableUseCase @Inject constructor(
    private val repository: MeRepository,
) {
    suspend operator fun invoke(): Result<Boolean> {
        return runCatching { repository.getIsProceedEnable() }
    }
}
