/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.search.viewmodel.sideeffect

internal sealed class SearchSideEffect {
    /**
     * [SearchViewModel] 의 비즈니스 로직 처리 중에 발생한 예외를 [exception] 으로 받고
     * 해당 exception 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     */
    class ReportError(val exception: Throwable) : SearchSideEffect()

    class NavigateToDetail(val examId: Int) : SearchSideEffect()

    class NavigateToUserProfile(val userId: Int) : SearchSideEffect()

    class SendToast(val message: String) : SearchSideEffect()

    object ExamRefresh : SearchSideEffect()

    class CopyDynamicLink(val examId: Int) : SearchSideEffect()
}
