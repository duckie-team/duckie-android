/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.category.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.tag.model.TagData

data class CategoryData(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("name")
    val name: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("popularTags")
    val popularTags: List<TagData>? = null,
)
