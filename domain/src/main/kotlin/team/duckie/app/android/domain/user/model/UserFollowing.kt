/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.category.model.Category

@Immutable
data class UserFollowing(
    val followingRecommendations: List<UserFollowingRecommendations>,
)

@Immutable
data class UserFollowingRecommendations(
    val category: Category? = null,
    val user: List<User>? = null,
)
