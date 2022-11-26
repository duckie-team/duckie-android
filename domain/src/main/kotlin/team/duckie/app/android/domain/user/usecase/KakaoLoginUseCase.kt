/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.repository.KakaoLoginRepository

@Immutable
class KakaoLoginUseCase(
    private val repository: KakaoLoginRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.login()
    }
}
