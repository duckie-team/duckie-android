/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.follow.model.FollowData

internal data class UserFollowingResponse(
    @field:JsonProperty("category")
    val category: CategoryData? = null,

    @field:JsonProperty("users")
    val users: List<UserResponse>? = null,

    @field:JsonProperty("duckPower")
    val duckPower: DuckPowerResponse? = null,

    @field:JsonProperty("follow")
    val follow: FollowData? = null,
)

internal data class UserFollowingsResponse(
    @field:JsonProperty("userRecommendations")
    val userRecommendations: List<UserFollowingResponse>? = null,
)
