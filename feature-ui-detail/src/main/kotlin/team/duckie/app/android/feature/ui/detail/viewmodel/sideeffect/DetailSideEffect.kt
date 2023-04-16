/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect

import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel

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

    /**
     * 시험 시작 화면으로 이동하기 위한 부수 효과 입니다.
     * 시험 시작을 위해 필요한 데이터를 함께 보내줍니다.
     *
     * @param examId 시험 id
     * @param certifyingStatement 필적 확인 문구
     */
    class StartExam(val examId: Int, val certifyingStatement: String) : DetailSideEffect()
}
