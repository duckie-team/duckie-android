/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
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

    @RequiredStep(OnboardStep.Login, OnboardStep.Tag) // 마지막 단계에서 유저 정보 업데이트함
    class UpdateUser(val user: User) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Login)
    class Joined(val isNewUser: Boolean) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Profile)
    class NicknameDuplicateChecked(val isUsable: Boolean) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Category)
    class CategoriesLoaded(val catagories: ImmutableList<Category>) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Tag)
    class TagCreated(val tag: Tag) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Tag) // 마지막 단계에서 유저 정보 업데이트함
    class PrfileImageUploaded(val url: String) : OnboardSideEffect()

    @RequiredStep(OnboardStep.Activity, OnboardStep.Tag)
    object FinishOnboard : OnboardSideEffect()

    class NavigateStep(val step: OnboardStep) : OnboardSideEffect()
    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
