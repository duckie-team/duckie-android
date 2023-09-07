/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

sealed class ExamResultSideEffect {
    object FinishExamResult : ExamResultSideEffect()

    class NavigateToStartExam(
        val examId: Int,
        val requirementQuestion: String,
        val requirementPlaceholder: String,
        val timer: Int,
    ) : ExamResultSideEffect()

    class ReportError(val exception: Throwable) : ExamResultSideEffect()

    object SendReactionSuccessToast : ExamResultSideEffect()

    object SendDeleteCommentSuccessToast : ExamResultSideEffect()

    object SendReportCommentSuccessToast : ExamResultSideEffect()

    data class SendIgnoreUserToast(val nickname: String) : ExamResultSideEffect()

    data class SendErrorToast(val message: String) : ExamResultSideEffect()
}
