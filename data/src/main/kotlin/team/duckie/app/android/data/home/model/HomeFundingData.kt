/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.home.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.heart.model.HeartData
import team.duckie.app.android.data.user.model.UserResponse

internal data class HomeFundingData(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("totalProblemCount")
    val totalProblemCount: Int? = null,

    @field:JsonProperty("problemCount")
    val problemCount: Int? = null,

    @field:JsonProperty("contributorCount")
    val contributorCount: Int? = null,

    @field:JsonProperty("user")
    val user: UserResponse? = null,

    @field:JsonProperty("heart")
    val heart: HeartData? = null,
)
