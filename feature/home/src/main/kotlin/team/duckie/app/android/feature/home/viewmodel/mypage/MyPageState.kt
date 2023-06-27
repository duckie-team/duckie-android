/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.mypage

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.profile.dummy.skeletonUserProfile
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileStep

internal data class MyPageState(
    val step: ProfileStep = ProfileStep.Profile,
    val isLoading: Boolean = true,
    val userProfile: UserProfile = skeletonUserProfile(),
    val me: User? = null,
)
