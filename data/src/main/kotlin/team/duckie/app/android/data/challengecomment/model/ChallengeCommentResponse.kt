/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.challengecomment.model

import android.service.autofill.UserData
import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.heart.model.HeartData
import team.duckie.app.android.data.user.model.UserResponse

data class ChallengeCommentResponse(
    @field:JsonProperty("id")
    val id: Int? = null,
    @field:JsonProperty("message")
    val message: String? = null,
    @field:JsonProperty("wrongAnswer")
    val wrongAnswer: String? = null,
    @field:JsonProperty("heartCount")
    val heartCount: Int? = null,
    @field:JsonProperty("createdAt")
    val createdAt: String? = null,
    @field:JsonProperty("user")
    val user: UserResponse? = null,
    @field:JsonProperty("heart")
    val heart: HeartData? = null,
)
