/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel.state

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserProfile

data class MyPageState(
    val isLoading: Boolean = true,
    val userProfile: UserProfile = UserProfile.empty(),
    val me: User? = null,
)
