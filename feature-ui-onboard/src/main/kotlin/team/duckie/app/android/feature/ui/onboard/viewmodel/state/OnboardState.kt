/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.constant.RequiredStep

internal sealed class OnboardState {
    object Initial : OnboardState()
    class NavigateStep(val step: OnboardStep) : OnboardState()

    @RequiredStep(OnboardStep.Login)
    class Joined(val isNewUser: Boolean) : OnboardState()

    @RequiredStep(OnboardStep.Profile)
    class NicknameDuplicateChecked(val isUsable: Boolean) : OnboardState()

    @RequiredStep(OnboardStep.Category)
    class CategoriesLoaded(val catagories: ImmutableList<Category>) : OnboardState()

    @RequiredStep(OnboardStep.Tag)
    class TagCreated(val tag: Tag) : OnboardState()

    @RequiredStep(OnboardStep.Tag) // 마지막 단계에서 유저 정보 업데이트함
    class PrfileImageUploaded(val url: String) : OnboardState()

    class Error(val exception: Throwable) : OnboardState()
}
