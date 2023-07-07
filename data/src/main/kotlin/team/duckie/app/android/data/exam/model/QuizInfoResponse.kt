/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

data class QuizInfoResponse(
    @field:JsonProperty("id")
    val id: Int? = null,
    @field:JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @field:JsonProperty("score")
    val score: Int? = null,
    @field:JsonProperty("time")
    val time: Int? = null,
    @field:JsonProperty("user")
    val user: UserResponse? = null,
    @field:JsonProperty("reaction")
    val reaction: String? = null,
)
