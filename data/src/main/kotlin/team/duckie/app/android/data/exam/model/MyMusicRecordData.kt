/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

data class MyMusicRecordData(
    @field:JsonProperty("user")
    val user: UserResponse? = null,
    @field:JsonProperty("takenTime")
    val takenTime: Float? = null,
    @field:JsonProperty("score")
    val score: Float? = null,
    @field:JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @field:JsonProperty("ranking")
    val ranking: Int? = null,
    @field:JsonProperty("status")
    val status: String? = null,
)
