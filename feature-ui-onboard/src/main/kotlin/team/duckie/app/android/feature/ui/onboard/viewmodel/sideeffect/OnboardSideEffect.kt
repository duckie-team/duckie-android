/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.constant.RequiredStep

internal sealed class OnboardSideEffect {
    object FinishOnboard : OnboardSideEffect()
    class UpdateGalleryImages(val images: List<String>) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class DelegateJoin(val kakaoAccessToken: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class UpdateAccessToken(val accessToken: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class AttachAccessTokenToHeader(val accessToken: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login, OnboardStep.Tag) // 마지막 단계에서 유저 정보 업데이트함
    class UpdateUser(val user: User) : OnboardSideEffect()

    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
