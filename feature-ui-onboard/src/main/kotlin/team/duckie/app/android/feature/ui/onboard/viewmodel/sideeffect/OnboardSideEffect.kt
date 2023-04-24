/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.onboard.constant.CollectInStep
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.constant.RequiredStep

internal sealed class OnboardSideEffect {
    @RequiredStep(OnboardStep.Activity)
    class UpdateGalleryImages(val images: List<String>) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class DelegateJoin(val kakaoAccessToken: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class UpdateAccessToken(val accessToken: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class AttachAccessTokenToHeader(val accessToken: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class Joined(val isNewUser: Boolean, val me: User) : OnboardSideEffect()

    @CollectInStep
    @RequiredStep(OnboardStep.Profile)
    class NicknameDuplicateChecked(val isUsable: Boolean) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Activity, OnboardStep.Tag)
    class FinishOnboard(val isNewUser: Boolean, val userId: String?) : OnboardSideEffect()

    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
