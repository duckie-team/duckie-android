/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.usecase

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import team.duckie.app.android.domain.quiz.repository.QuizRepository
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
        )
    }

    @Parcelize
    data class Param(
        val correctProblemCount: Int,
        val time: Int?,
        val problemId: Int?,
    ) : Parcelable
}
