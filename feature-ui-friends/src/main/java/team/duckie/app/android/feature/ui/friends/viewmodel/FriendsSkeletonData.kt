/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.feature.ui.friends.viewmodel.state.FriendsState

internal val skeletonFriends: ImmutableList<FriendsState.Friend> = (0..2).map {
    FriendsState.Friend.empty()
}.toImmutableList()
