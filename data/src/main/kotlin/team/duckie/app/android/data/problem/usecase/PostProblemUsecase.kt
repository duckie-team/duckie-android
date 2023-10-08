/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.problem.usecase

import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.problem.repository.ProblemRepository
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
class PostProblemUseCase @Inject constructor(
    private val repository: ProblemRepository,
) {
    suspend operator fun invoke(
        correctAnswer: String,
        question: Question,
        answer: Answer,
        examId: Int,
        wrongAnswerMessage: String,
        solutionImageUrl: String,
        memo: String = "",
        hint: String = "",
    ): Result<Problem> {
        return runCatching {
            repository.postProblem(
                correctAnswer,
                question,
                answer,
                examId,
                wrongAnswerMessage,
                solutionImageUrl,
                memo,
                hint,
            )
        }
    }
}
