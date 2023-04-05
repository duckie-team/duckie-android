/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel.sideeffect

sealed class ProfileSideEffect {

    class ReportError(val exception: Throwable) : ProfileSideEffect()

    class NavigateToExamDetail(val examId: Int) : ProfileSideEffect()

    object NavigateToBack : ProfileSideEffect()
}
