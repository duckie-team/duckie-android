/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.viewmodel

internal sealed class StartExamSideEffect {
    object FinishStartExam : StartExamSideEffect()

    data class NavigateToSolveProblem(
        val certified: Boolean,
        val examId: Int,
        val isQuiz: Boolean,
    ) : StartExamSideEffect()

    data class ReportError(val exception: Throwable) : StartExamSideEffect()
}
