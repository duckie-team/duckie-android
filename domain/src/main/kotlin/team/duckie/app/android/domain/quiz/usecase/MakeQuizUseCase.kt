/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.quiz.repository.QuizRepository
import javax.inject.Inject

@Immutable
class MakeQuizUseCase @Inject constructor(
    private val repository: QuizRepository,
) {
    suspend operator fun invoke(examId: Int) = runCatching {
        repository.makeQuiz(examId)
    }
}
