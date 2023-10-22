/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.problem.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question

@Immutable
data class ProblemBody(
    val question: Question?,
    val answer: Answer?,
    val correctAnswer: String?,
    val examId: Int?,
    val wrongAnswerMessage: String?,
    val solutionImageUrl: String?,
    val hint: String?,
    val memo: String?,
    val status: String?,
)
