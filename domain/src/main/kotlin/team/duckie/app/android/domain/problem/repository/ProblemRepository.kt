/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.problem.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question

@Immutable
interface ProblemRepository {
    suspend fun patchProblem(problemId: Int, status: String, isSample: Boolean): Problem

    suspend fun postProblem(
        correctAnswer: String,
        question: Question,
        answer: Answer,
        examId: Int,
        wrongAnswerMessage: String,
        solutionImageUrl: String,
        memo: String = "",
        hint: String = "",
    ): Problem
}
