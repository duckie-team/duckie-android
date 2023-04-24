/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import team.duckie.app.android.util.kotlin.FriendsType

internal sealed class DuckieHomeSideEffect {

    /**
     * [DuckieHomeViewModel] 의 비즈니스 로직 처리 중에 발생한 예외를 [exception] 으로 받고
     * 해당 exception 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     */
    class ReportError(val exception: Throwable) : DuckieHomeSideEffect()

    /**
     * [SearchResultActivity] 로 이동하는 SideEffect 입니다.
     */
    class NavigateToSearch(val searchTag: String?) : DuckieHomeSideEffect()

    /**
     * [HomeDetailActivity] 로 이동하는 SideEffect 입니다.
     */
    class NavigateToDuckieHomeDetail(val examId: Int) : DuckieHomeSideEffect()

    /**
     * [SettingActivity] 로 이동하는 SideEffect 입니다.
     */
    object NavigateToSetting : DuckieHomeSideEffect()

    /**
     * [CreateProblemActivity] 로 이동하는 SideEffect 입니다.
     */
    object NavigateToCreateProblem : DuckieHomeSideEffect()

    object NavigateToNotification : DuckieHomeSideEffect()

    object ClickRankingRetry : DuckieHomeSideEffect()

    class NavigateToFriends(val friendType: FriendsType, val myUserId: Int) : DuckieHomeSideEffect()
}
