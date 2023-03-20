/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.kakao.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.kakao.repository.KakaoRepository
import team.duckie.app.android.util.kotlin.exception.isKakaoTalkNotSupportAccount

/**
 * 카카오 AccessToken 을 가져오는 유즈케이스
 *
 * https://devtalk.kakao.com/t/topic/128009
 * 위 이슈로 인해서 카카오톡에서 NotSupportException 을 발생시킬 경우
 * WebView 를 통해 로그인을 시도합니다.
 */
@Immutable
class GetKakaoAccessTokenUseCase(private val repository: KakaoRepository) {
    suspend operator fun invoke(): Result<String> {
        return runCatching {
            repository.getAccessToken()
        }.onFailure { exception ->
            when {
                exception.isKakaoTalkNotSupportAccount -> {
                    return runCatching {
                        repository.loginWithWebView()
                    }
                }
            }
        }
    }
}
