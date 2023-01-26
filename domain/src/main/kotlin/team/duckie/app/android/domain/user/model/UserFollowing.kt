/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import team.duckie.app.android.domain.category.model.Category

data class UserFollowing (
    val followingRecommendations: List<UserFollowingRecommendations>
)

data class UserFollowingRecommendations (
    val category: Category? = null,
    val user: List<User>? = null,
)
