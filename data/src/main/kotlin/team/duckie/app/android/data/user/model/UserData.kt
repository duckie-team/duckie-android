/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

internal data class UserData(
    @field:JsonProperty("id")
    val id: Int,
    @field:JsonProperty("nickName")
    val nickName: String,
    @field:JsonProperty("accountEnabled")
    val accountEnabled: Boolean,
    @field:JsonProperty("profileUrl")
    val profileUrl: String,
    @field:JsonProperty("tier")
    val tier: Int,
    @field:JsonProperty("createdAt")
    val createdAt: Date,
    @field:JsonProperty("updatedAt")
    val updatedAt: Date,
    @field:JsonProperty("deletedAt")
    val deletedAt: Date? = null,
    @field:JsonProperty("bannedAt")
    val bannedAt: Date? = null,
)
