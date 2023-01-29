/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.tag.model.TagData

internal data class UserResponse(

    @field:JsonProperty("nickName")
    val nickName: String? = null,

    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String? = null,

    @field:JsonProperty("duckPower")
    val duckPower: DuckPowerResponse? = null,

    @field:JsonProperty("favoriteTags")
    val favoriteTags: List<TagData>? = null,

    @field:JsonProperty("favoriteCategories")
    val favoriteCategories: List<CategoryData>? = null,
)
