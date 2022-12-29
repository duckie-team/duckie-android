/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.kakao.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.kakao.model.KakaoUser

/**
 * 카카오 관련 작업을 진행하는 repository 입니다.
 */
@Immutable
interface KakaoRepository {
    /**
     * 카카오 로그인을 요청합니다.
     *
     * @return 카카오 로그인에 성공하면 [KakaoUser] 객체를 반환합니다.
     *
     * @throws IllegalStateException 카카오 로그인에 실패한 경우
     */
    suspend fun login(): KakaoUser
}
