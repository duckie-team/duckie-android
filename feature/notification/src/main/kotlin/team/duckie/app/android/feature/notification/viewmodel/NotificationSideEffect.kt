/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.notification.viewmodel

sealed class NotificationSideEffect {
    class ReportError(val exception: Throwable) : NotificationSideEffect()
    object FinishActivity : NotificationSideEffect()
    object NavigateToMyPage : NotificationSideEffect()
}
