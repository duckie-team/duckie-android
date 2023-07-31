/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel

import team.duckie.app.android.common.kotlin.FriendsType

internal sealed class MainSideEffect {

    /**
     * [MainViewModel] 의 비즈니스 로직 처리 중에 발생한 예외를 [exception] 으로 받고
     * 해당 exception 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     */
    class ReportError(val exception: Throwable) : MainSideEffect()

    /**
     * [SearchResultActivity] 로 이동하는 SideEffect 입니다.
     */
    class NavigateToSearch(val searchTag: String?) : MainSideEffect()

    /**
     * [HomeDetailActivity] 로 이동하는 SideEffect 입니다.
     */
    class NavigateToMainDetail(val examId: Int) : MainSideEffect()

    /**
     * [SettingActivity] 로 이동하는 SideEffect 입니다.
     */
    object NavigateToSetting : MainSideEffect()

    /**
     * [CreateProblemActivity] 로 이동하는 SideEffect 입니다.
     */
    object NavigateToCreateProblem : MainSideEffect()

    object NavigateToNotification : MainSideEffect()

    data class NavigateToProfile(val userId: Int) : MainSideEffect()

    object ClickRankingRetry : MainSideEffect()

    class NavigateToFriends(val friendType: FriendsType, val myUserId: Int, val nickname: String) :
        MainSideEffect()

    class SendToast(val message: String) : MainSideEffect()

    data class CopyExamIdDynamicLink(val examId: Int) : MainSideEffect()
}
