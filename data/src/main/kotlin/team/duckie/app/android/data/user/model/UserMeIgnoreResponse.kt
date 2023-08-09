/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserMeIgnoreResponse(
    @field:JsonProperty("users")
    val users: List<IgnoreUserResponse>? = null,
)

data class IgnoreUserResponse(
    @field:JsonProperty("id") val id: Int? = null,
    @field:JsonProperty("nickName") val nickName: String? = null,
    @field:JsonProperty("profileImageUrl") val profileImageUrl: String? = null,
    @field:JsonProperty("duckPower") val duckPower: DuckPowerResponse? = null,
    @field:JsonProperty("userBlock") val userBlock: UserBlockResponse? = null,
    @field:JsonProperty("permissions") val permissions: List<String>? = null,
)
