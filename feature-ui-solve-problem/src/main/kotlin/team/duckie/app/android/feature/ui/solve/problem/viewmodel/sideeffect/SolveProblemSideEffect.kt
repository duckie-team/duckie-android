/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.viewmodel.sideeffect

internal sealed class SolveProblemSideEffect {
    class FinishSolveProblem(
        val examId: Int,
        val answers: List<String>,
    ) : SolveProblemSideEffect()

    class FinishQuiz(
        val examId: Int,
        val correctProblemCount: Int,
        val time: Int,
        val problemId: Int,
    ) : SolveProblemSideEffect()

    class ReportError(val exception: Throwable) : SolveProblemSideEffect()

    object NavigatePreviousScreen : SolveProblemSideEffect()

    class MoveNextPage(val maxPage: Int) : SolveProblemSideEffect()
}
