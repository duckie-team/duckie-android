/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.repository

import team.duckie.app.android.data.quiz.datasource.QuizDataSource
import team.duckie.app.android.data.quiz.model.PatchQuizBody
import team.duckie.app.android.domain.quiz.model.Quiz
import team.duckie.app.android.domain.quiz.model.QuizResult
import team.duckie.app.android.domain.quiz.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDataSource: QuizDataSource,
) : QuizRepository {
    override suspend fun makeQuiz(examId: Int): Quiz {
        return quizDataSource.postQuiz(examId)
    }

    override suspend fun getQuiz(examId: Int): QuizResult {
        return quizDataSource.getQuizs(examId)
    }

    override suspend fun submitQuiz(
        examId: Int,
        correctProblemCount: Int,
        time: Int?,
        requirementAnswer: String?,
        problemId: Int?,
        wrongAnswer: String?,
    ): Boolean {
        return quizDataSource.patchQuiz(
            examId = examId,
            body = PatchQuizBody(
                correctProblemCount = correctProblemCount,
                time = time,
                problemId = problemId,
                requirementAnswer = requirementAnswer,
                wrongAnswer = wrongAnswer,
            ),
        )
    }
}
