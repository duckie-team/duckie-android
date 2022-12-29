/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.kakao.mapper

import com.kakao.sdk.user.model.User
import team.duckie.app.android.domain.kakao.model.DefaultProfilePhoto
import team.duckie.app.android.domain.kakao.model.KakaoUser

internal fun User.toKakaoUser(defaultName: String) = KakaoUser(
    name = kakaoAccount?.profile?.nickname ?: defaultName,
    profilePhotoUrl = kakaoAccount?.profile?.profileImageUrl ?: KakaoUser.DefaultProfilePhoto,
    accountEmail = kakaoAccount?.email.orEmpty(),
)
