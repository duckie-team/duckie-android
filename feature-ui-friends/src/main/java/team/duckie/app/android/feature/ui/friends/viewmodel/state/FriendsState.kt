/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends.viewmodel.state

import team.duckie.app.android.util.kotlin.FriendsType

internal data class FriendsState(
    val isLoading: Boolean = true,
    val selectedTab: FriendsType = FriendsType.Follower,
)
