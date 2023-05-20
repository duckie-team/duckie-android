/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.friends.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.friends.viewmodel.skeletonFriends
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.common.kotlin.randomString

internal data class FriendsState(
    val me: User ? = null,

    val friendType: FriendsType = FriendsType.Follower,
    val targetName: String = "",

    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isMine: Boolean = true,

    val followers: ImmutableList<Friend> = skeletonFriends,
    val followings: ImmutableList<Friend> = skeletonFriends,
) {
    data class Friend(
        val userId: Int,
        val profileImgUrl: String,
        val nickname: String,
        val favoriteTag: String,
        val tier: String,
        val isFollowing: Boolean,
    ) {
        companion object {
            fun empty() = Friend(
                userId = 0,
                profileImgUrl = "",
                nickname = randomString(length = 5),
                favoriteTag = randomString(length = 5),
                tier = randomString(length = 5),
                isFollowing = false,
            )
        }
    }
}
