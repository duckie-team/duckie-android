/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.terms.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class TermsResponseData(
    @field:JsonProperty("createdAt")
    val createdAt: String? = null,

    @field:JsonProperty("condition")
    val condition: String? = null,

    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("version")
    val version: String? = null,
)
