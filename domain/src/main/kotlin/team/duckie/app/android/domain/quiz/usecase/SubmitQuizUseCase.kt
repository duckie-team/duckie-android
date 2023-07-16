/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:Suppress("SerialVersionUIDInSerializableClass")

package team.duckie.app.android.domain.quiz.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.quiz.repository.QuizRepository
import java.io.Serializable
import javax.inject.Inject

@Immutable
class SubmitQuizUseCase @Inject constructor(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(
        examId: Int,
        param: Param,
    ) = runCatching {
        repository.submitQuiz(
            examId = examId,
            correctProblemCount = param.correctProblemCount,
            time = param.time,
            problemId = param.problemId,
            requirementAnswer = param.requirementAnswer,
            wrongAnswer = param.wrongAnswer,
        )
    }

    @Suppress("SerialVersionUIDInSerializableClass")
    data class Param(
        val correctProblemCount: Int,
        val time: Double?,
        val problemId: Int?,
        val requirementAnswer: String?,
        val wrongAnswer: String?,
    ) : Serializable {
        companion object {
            private const val serialVersionUID: Long = 1L
        }
    }
}
