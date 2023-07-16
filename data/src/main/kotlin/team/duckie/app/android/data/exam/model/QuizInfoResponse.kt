/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

/**
 * API 명세서 상에서 challenge entity를 의미합니다.
 */
data class QuizInfoResponse(
    @field:JsonProperty("id")
    val id: Int? = null,
    @field:JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @field:JsonProperty("score")
    val score: Int? = null,
    @field:JsonProperty("user")
    val user: UserResponse? = null,
    @field:JsonProperty("time")
    val time: Double? = null,
    @field:JsonProperty("reaction")
    val reaction: String? = null,
    @field:JsonProperty("ranking")
    val ranking: Int? = null,
)
