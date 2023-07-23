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
class PostQuizReactionUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(
        examId: Int,
        reaction: String,
    ) = kotlin.runCatching {
        quizRepository.postQuizReaction(
            examId = examId,
            reaction = reaction,
        )
    }
}
