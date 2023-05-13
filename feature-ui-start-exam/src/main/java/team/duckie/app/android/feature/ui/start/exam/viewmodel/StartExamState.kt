/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.viewmodel

import team.duckie.app.android.feature.ui.start.exam.screen.StartExamActivity

sealed class StartExamState {
    /** [StartExamActivity] 가 로딩 중임을 나타냅니다. */
    object Loading : StartExamState()

    /** [StartExamViewModel] 데이터를 잘 받았을 때의 상태를 나타냅니다. */
    data class Input(
        val examId: Int,
        val certifyingStatement: String,
        val certifyingStatementInputText: String = "",
        val isQuiz: Boolean = true,
    ) : StartExamState() {
        val isCertified: Boolean
            get() = certifyingStatement == certifyingStatementInputText
    }

    /**
     * [StartExamViewModel] 의 비즈니스 로직 처리중에 예외가 발생한 상태를 나타냅니다.
     *
     * @param exception 발생한 예외
     */
    class Error(val exception: Throwable) : StartExamState()
}
