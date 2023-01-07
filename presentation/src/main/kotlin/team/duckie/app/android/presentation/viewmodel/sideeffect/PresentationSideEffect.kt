/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel.sideeffect

internal sealed class PresentationSideEffect {
    class AttachAccessTokenToHeader(val accessToken: String) : PresentationSideEffect()

    class ReportError(val exception: Throwable) : PresentationSideEffect()
}
