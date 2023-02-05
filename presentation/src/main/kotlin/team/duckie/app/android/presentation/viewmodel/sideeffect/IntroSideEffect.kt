/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel.sideeffect

internal sealed class IntroSideEffect {
    class SetMeInstance(val userId: Int) : IntroSideEffect()
    class AttachAccessTokenToHeader(val accessToken: String) : IntroSideEffect()

    class ReportError(val exception: Throwable) : IntroSideEffect()
}
