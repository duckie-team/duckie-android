/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.repository.KakaoRepository

@Immutable
class KakaoLoginUseCase(
    private val repository: KakaoRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.login()
    }
}
