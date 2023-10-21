/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.problem.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.QuestionData

internal data class ProblemBodyData(
    @JsonProperty("answer")
    val answer: AnswerData?,

    @JsonProperty("correctAnswer")
    val correctAnswer: String?,

    @JsonProperty("examId")
    val examId: Int?,

    @JsonProperty("hint")
    val hint: String?,

    @JsonProperty("memo")
    val memo: String?,

    @JsonProperty("question")
    val question: QuestionData?,

    @JsonProperty("solutionImageUrl")
    val solutionImageUrl: String?,

    @JsonProperty("wrongAnswerMessage")
    val wrongAnswerMessage: String?,
)
