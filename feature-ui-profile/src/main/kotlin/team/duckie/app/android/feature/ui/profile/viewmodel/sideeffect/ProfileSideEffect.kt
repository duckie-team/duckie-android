/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel.sideeffect

import team.duckie.app.android.util.kotlin.FriendsType

sealed class ProfileSideEffect {

    class ReportError(val exception: Throwable) : ProfileSideEffect()

    object NavigateToBack : ProfileSideEffect()

    object NavigateToFollowPage : ProfileSideEffect()

    class NavigateToSearch(val tagName: String) : ProfileSideEffect()
    object NavigateToSetting : ProfileSideEffect()

    object NavigateToNotification : ProfileSideEffect()

    object NavigateToMakeExam : ProfileSideEffect()

    class NavigateToExamDetail(val examId: Int) : ProfileSideEffect()

    class NavigateToEditProfile(val userId: Int) : ProfileSideEffect()

    class NavigateToTagEdit(val userId: Int) : ProfileSideEffect()

    class SendToast(val message: String) : ProfileSideEffect()

    class NavigateToFriends(
        val friendType: FriendsType,
        val userId: Int,
        val nickname: String,
    ) : ProfileSideEffect()
}
