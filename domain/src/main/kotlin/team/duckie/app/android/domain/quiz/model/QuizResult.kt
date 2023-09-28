/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.user.model.User

@Immutable
data class QuizResult(
    val id: Int,
    val correctProblemCount: Int,
    val exam: Exam,
    val score: Int,
    val time: Double,
    val user: User,
    val wrongProblem: Problem?,
    val wrongAnswer: WrongAnswer?,
    val ranking: Int?,
    val requirementAnswer: String?,
    val isBestRecord: Boolean,
    val popularComments: ImmutableList<ChallengeComment>?,
    val commentsTotal: Int?,
    val solveRank: Int?,
    val percentileRank: String?,
) {
    @Immutable
    data class WrongAnswer(
        val meTotal: Int,
        val meData: String,
        val mostTotal: Int,
        val mostData: String,
    )
}
