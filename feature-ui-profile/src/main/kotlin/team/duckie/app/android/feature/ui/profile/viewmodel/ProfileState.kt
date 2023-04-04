/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel

import team.duckie.app.android.domain.user.model.UserProfile

data class ProfileState(
    val isMe : Boolean = false,
    val userProfile: UserProfile = UserProfile.empty(),
)
