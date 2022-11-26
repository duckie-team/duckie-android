/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.feature.ui.onboard.viewmodel.constaint.OnboardStep

internal sealed class OnboardState {
    object Initial : OnboardState()
    class NavigateStep(val step: OnboardStep) : OnboardState()
    class GalleryImageLoaded(val images: ImmutableList<String>) : OnboardState()
    class Error(val exception: Throwable) : OnboardState()
}
