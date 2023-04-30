/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.mypage

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.ui.profile.dummy.skeletonUserProfile

internal data class MyPageState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val userProfile: UserProfile = skeletonUserProfile(),
    val me: User? = null,
)
