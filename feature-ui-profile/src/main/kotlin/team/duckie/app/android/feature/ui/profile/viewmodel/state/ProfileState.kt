/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel.state

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.ui.profile.dummy.skeletonUserProfile

data class ProfileState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val userProfile: UserProfile = skeletonUserProfile(),
    val me: User? = null,
    val isMe: Boolean = false,
    val reportDialogVisible: Boolean = false,
    val follow: Boolean = false,
    val reportExamId: Int = 0,
    val userId: Int = 0,
)
