/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.follow.model.Follow

// TODO(riflockle7): value class 적용 검토하기
@Immutable
data class UserFollowings(
    val followingRecommendations: List<UserFollowing>,
)

@Immutable
data class UserFollowing(
    val category: Category,
    val users: List<User>,
)
