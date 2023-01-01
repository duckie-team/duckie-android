/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep

internal sealed class OnboardState {
    object Initial : OnboardState()

    class Joined(val isNewUser: Boolean) : OnboardState()
    class NavigateStep(val step: OnboardStep) : OnboardState()
    object AccessTokenValidationFailed : OnboardState()

    class CategoriesLoaded(val catagories: ImmutableList<Category>) : OnboardState()
    class FileUploaded(val url: String) : OnboardState()
    class TagCreated(val id: Int) : OnboardState()

    class Error(val exception: Throwable) : OnboardState()
}
