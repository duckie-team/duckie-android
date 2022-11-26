/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable

/**
 * 카카오 로그인으로 받아온 유저 객체에서 덕키가 필요로 하는
 * 정보만 나타냅니다.
 *
 * @param name 유저의 닉네임
 * @param profilePhotoUrl 유저의 프로필 사진 링크
 * @param accountEmail 계정의 이메일. 선택적으로 값을 받습니다.
 */
@Immutable
data class KakaoUser(
    val name: String,
    val profilePhotoUrl: String,
    val accountEmail: String?,
)
