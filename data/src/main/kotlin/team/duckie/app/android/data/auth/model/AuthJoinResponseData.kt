/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.auth.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

internal data class AuthJoinResponseData(
    @field:JsonProperty("isNewUser")
    val isNewUser: Boolean? = null,

    @field:JsonProperty("accessToken")
    val accessToken: String? = null,

    @field:JsonProperty("user")
    val user: UserResponse? = null,
)
