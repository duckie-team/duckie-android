/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.datasource

import team.duckie.app.android.data.quiz.model.PatchQuizBody
import team.duckie.app.android.domain.quiz.model.Quiz
import team.duckie.app.android.domain.quiz.model.QuizResult

interface QuizDataSource {
    suspend fun postQuiz(examId: Int): Quiz
    suspend fun getQuizs(examId: Int): QuizResult
    suspend fun patchQuiz(body: PatchQuizBody): Boolean
}
