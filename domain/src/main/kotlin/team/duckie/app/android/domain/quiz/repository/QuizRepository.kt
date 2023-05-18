/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.quiz.model.Quiz
import team.duckie.app.android.domain.quiz.model.QuizResult

@Immutable
interface QuizRepository {
    suspend fun makeQuiz(examId: Int): Quiz

    suspend fun getQuiz(examId: Int): QuizResult

    suspend fun submitQuiz(
        examId: Int,
        correctProblemCount: Int,
        time: Int?,
        problemId: Int?,
    ): Boolean
}
