/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.kakao.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.kakao.repository.KakaoRepository

@Immutable
class GetKakaoAccessTokenUseCase(private val repository: KakaoRepository) {
    suspend operator fun invoke(): Result<String> {
        return runCatching {
            repository.getAccessToken()
        }
    }
}
