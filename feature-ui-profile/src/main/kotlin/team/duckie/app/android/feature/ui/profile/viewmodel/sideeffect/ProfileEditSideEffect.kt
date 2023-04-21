/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel.sideeffect

sealed class ProfileEditSideEffect {
    object NavigateBack : ProfileEditSideEffect()
    class ReportError(val exception: Throwable) : ProfileEditSideEffect()
}
