/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.FriendsType

internal data class FriendsState(
    val me: User ?= null,

    val isLoading: Boolean = true,
    val selectedTab: FriendsType = FriendsType.Follower,

    val followers: ImmutableList<User> = persistentListOf(),
    val followings: ImmutableList<User> = persistentListOf(),
)
