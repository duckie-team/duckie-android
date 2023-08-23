/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.user.model.UserResponse

internal data class GetQuizResponse(
    @field:JsonProperty("id")
    val id: Int? = null,
    @field:JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @field:JsonProperty("exam")
    val exam: ExamData? = null,
    @field:JsonProperty("score")
    val score: Int? = null,
    @field:JsonProperty("time")
    val time: Double? = null,
    @field:JsonProperty("user")
    val user: UserResponse? = null,
    @field:JsonProperty("wrongProblem")
    val wrongProblem: ProblemData? = null,
    @field:JsonProperty("wrongAnswer")
    val wrongAnswer: MeAndMostWrongAnswer? = null,
    @field:JsonProperty("ranking")
    val ranking: Int? = null,
    @field:JsonProperty("requirementAnswer")
    val requirementAnswer: String? = null,
    @field:JsonProperty("isBestRecord")
    val isBestRecord: Boolean? = null,
) {
    data class MeAndMostWrongAnswer(
        @field:JsonProperty("me")
        val me: SimpleWrongAnswer,
        @field:JsonProperty("most")
        val most: SimpleWrongAnswer,
    )
    data class SimpleWrongAnswer(
        @field:JsonProperty("total")
        val total: Int,
        @field:JsonProperty("data")
        val data: String,
    )
}
