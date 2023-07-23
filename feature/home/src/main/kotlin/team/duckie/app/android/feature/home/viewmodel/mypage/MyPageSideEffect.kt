/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.mypage

import team.duckie.app.android.feature.profile.viewmodel.state.ExamType

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

    class NavigateToViewAll(
        val userId: Int,
        val examType: ExamType,
    ) : MyPageSideEffect()
}
