/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.state

import team.duckie.app.android.domain.user.model.KakaoUser

sealed class KakaoLoginState {
    object Initial : KakaoLoginState()
    class Success(val user: KakaoUser) : KakaoLoginState()
    class Error(val exception: Throwable) : KakaoLoginState()
}
