/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.onboard.constant.CollectInStep
import team.duckie.app.android.feature.ui.onboard.constant.CollectInViewModel
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
    class Joined(val isNewUser: Boolean) : OnboardSideEffect()

    @CollectInStep
    @RequiredStep(OnboardStep.Profile)
    class NicknameDuplicateChecked(val isUsable: Boolean) : OnboardSideEffect()

    @CollectInViewModel
    @RequiredStep(OnboardStep.Tag)
    class TagCreated(val tag: Tag) : OnboardSideEffect()

    @CollectInViewModel
    @RequiredStep(OnboardStep.Tag) // 마지막 단계에서 유저 정보 업데이트함
    class PrfileImageUploaded(val url: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Activity, OnboardStep.Tag)
    object FinishOnboard : OnboardSideEffect()

    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
