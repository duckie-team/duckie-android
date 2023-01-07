/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel.state

internal sealed class PresentationState {
    object Initial : PresentationState()

    object AccessTokenValidationFailed : PresentationState()
    object AttachedAccessTokenToHeader : PresentationState()

    class Error(val exception: Throwable) : PresentationState()
}
