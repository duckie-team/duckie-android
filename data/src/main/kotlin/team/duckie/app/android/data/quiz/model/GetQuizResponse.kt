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
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @JsonProperty("exam")
    val exam: ExamData? = null,
    @JsonProperty("score")
    val score: Int? = null,
    @JsonProperty("time")
    val time: Double? = null,
    @JsonProperty("user")
    val user: UserResponse? = null,
    @JsonProperty("wrongProblem")
    val wrongProblem: ProblemData? = null,
    @JsonProperty("ranking")
    val ranking: Int? = null,
    @JsonProperty("requirementAnswer")
    val requirementAnswer: String? = null,
)
