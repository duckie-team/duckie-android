/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.viewmodel.sideeffect

import team.duckie.app.android.feature.detail.viewmodel.DetailViewModel

/** [DetailViewModel] 에서 사용되는 SideEffect 모음 */
sealed class DetailSideEffect {
    class SendToast(val message: String) : DetailSideEffect()

    /**
     * [DetailViewModel] 의 비즈니스 로직 처리 중에 발생한 예외를 [exception] 으로 받고
     * 해당 exception 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     */
    class ReportError(val exception: Throwable) : DetailSideEffect()

    class StartExam(
        val examId: Int,
        val certifyingStatement: String,
        val isQuiz: Boolean,
    ) : DetailSideEffect()

    class StartQuiz(
        val examId: Int,
        val certifyingStatement: String,
        val isQuiz: Boolean,
    ) : DetailSideEffect()

    /**
     * [SearchActivity] 로 이동하는 SideEffect 입니다.
     */
    class NavigateToSearch(val searchTag: String?) : DetailSideEffect()

    class NavigateToMyPage(val userId: Int) : DetailSideEffect()
}
