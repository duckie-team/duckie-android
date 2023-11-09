/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.start.exam.viewmodel

import team.duckie.app.android.domain.recommendation.model.ExamType

internal sealed class StartExamSideEffect {
    object FinishStartExam : StartExamSideEffect()

    data class NavigateToSolveProblem(
        val certified: Boolean,
        val examId: Int,
        val examType: ExamType,
        val requirementAnswer: String,
    ) : StartExamSideEffect()

    data class ReportError(val exception: Throwable) : StartExamSideEffect()
}
