/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.quiz.repository.QuizRepository
import java.io.Serializable
import javax.inject.Inject

@Immutable
class UpdateQuizUseCase @Inject constructor(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(
        examId: Int,
        param: Param,
    ) = runCatching {
        repository.updateQuiz(
            examId = examId,
            correctProblemCount = param.correctProblemCount,
            time = param.time,
            problemId = param.problemId,
        )
    }

    class Param(
        val correctProblemCount: Int,
        val time: Int?,
        val problemId: Int?,
    ) : Serializable
}
