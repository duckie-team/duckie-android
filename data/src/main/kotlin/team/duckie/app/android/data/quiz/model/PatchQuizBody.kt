/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PatchQuizBody(
    @JsonProperty("problemId")
    val problemId: Int? = null,
    @JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @JsonProperty("time")
    val time: Double? = null,
    @JsonProperty("requirementAnswer")
    val requirementAnswer: String? = null,
    @JsonProperty("wrongAnswer")
    val wrongAnswer: String? = null,
)
