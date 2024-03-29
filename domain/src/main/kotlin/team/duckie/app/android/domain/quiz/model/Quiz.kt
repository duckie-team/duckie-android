/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.model.User

@Immutable
data class Quiz(
    val id: Int,
    val correctProblemCount: Int,
    val exam: QuizExam,
    val score: Int,
    val time: Double,
    val user: User,
)
