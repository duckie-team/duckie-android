/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.auth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenCheckResponseData(
    @field:JsonProperty("type")
    val type: String? = null,

    @field:JsonProperty("userId")
    val userId: Int? = null,
)
