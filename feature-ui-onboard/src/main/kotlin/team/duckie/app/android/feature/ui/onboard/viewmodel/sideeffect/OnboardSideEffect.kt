/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import team.duckie.app.android.domain.user.model.KakaoUser

internal sealed class OnboardSideEffect {
    class SaveUser(val user: KakaoUser) : OnboardSideEffect()
    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
