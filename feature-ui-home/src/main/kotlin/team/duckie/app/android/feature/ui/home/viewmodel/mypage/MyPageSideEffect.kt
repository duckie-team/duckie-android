/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.mypage

sealed class MyPageSideEffect {
    class ReportError(val exception: Throwable) : MyPageSideEffect()

    object NavigateToSetting : MyPageSideEffect()

    object NavigateToNotification : MyPageSideEffect()

    object NavigateToMakeExam : MyPageSideEffect()

    class NavigateToEditProfile(val userId: Int) : MyPageSideEffect()

    class NavigateToExamDetail(val examId: Int) : MyPageSideEffect()

    class NavigateToTagEdit(val userId: Int) : MyPageSideEffect()

    class SendToast(val message: String) : MyPageSideEffect()

    class NavigateToSearch(val tagName: String) : MyPageSideEffect()
}
