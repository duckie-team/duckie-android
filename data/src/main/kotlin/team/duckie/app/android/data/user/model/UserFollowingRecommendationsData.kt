/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.category.model.CategoryData

internal data class UserFollowingRecommendationsData(
    @JsonProperty("category")
    val category: CategoryData? = null,

    @JsonProperty("user")
    val user: List<UserResponse>? = null,
)
