/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.tag.model.TagData

data class DuckPowerResponse(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("tier")
    val tier: String? = null,

    @field:JsonProperty("tag")
    val tag: TagData? = null,
)
