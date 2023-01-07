/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag

internal data class UserResponse(
    @field:JsonProperty("tier")
    val tier: Int? = null,

    @field:JsonProperty("nickName")
    val nickName: String? = null,

    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String? = null,

    @field:JsonProperty("favoriteTags")
    val favoriteTags: List<Tag?>? = null,

    @field:JsonProperty("favoriteCategories")
    val favoriteCategories: List<Category?>? = null,
)
