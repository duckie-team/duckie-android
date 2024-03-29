/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.viewmodel.sideeffect

import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType

sealed class ProfileSideEffect {

    class ReportError(val exception: Throwable) : ProfileSideEffect()

    class NavigateToBack(val isFollow: Boolean, val userId: Int) : ProfileSideEffect()

    object NavigateToFollowPage : ProfileSideEffect()

    class NavigateToSearch(val tagName: String) : ProfileSideEffect()
    object NavigateToSetting : ProfileSideEffect()

    object NavigateToNotification : ProfileSideEffect()

    object NavigateToMakeExam : ProfileSideEffect()

    class NavigateToExamDetail(val examId: Int) : ProfileSideEffect()

    class NavigateToEditProfile(val userId: Int) : ProfileSideEffect()

    class NavigateToTagEdit(val userId: Int) : ProfileSideEffect()

    class SendToast(val message: String) : ProfileSideEffect()

    class NavigateToViewAll(
        val userId: Int,
        val examType: ExamType,
    ) : ProfileSideEffect()

    class NavigateToFriends(
        val friendType: FriendsType,
        val userId: Int,
        val nickname: String,
    ) : ProfileSideEffect()
}
