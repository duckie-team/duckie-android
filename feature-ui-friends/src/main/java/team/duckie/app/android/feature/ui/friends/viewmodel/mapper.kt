/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends.viewmodel

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.friends.viewmodel.state.FriendsState

internal fun User.toUiModel() = FriendsState.Friend(
    userId = id,
    profileImgUrl = profileImageUrl ?: "",
    nickname = nickname,
    favoriteTag = duckPower?.tag?.name ?: "",
    tier = duckPower?.tier ?: "",
    isFollowing = follow != null,
)
