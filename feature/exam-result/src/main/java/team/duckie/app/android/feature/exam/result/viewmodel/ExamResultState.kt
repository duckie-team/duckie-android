/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

enum class ExamResultScreen {
    EXAM_RESULT,
    SHARE_EXAM_RESULT,
}

sealed class ExamResultState {
    object Loading : ExamResultState()

    data class Success(
        val currentScreen: ExamResultScreen = ExamResultScreen.EXAM_RESULT,

        val reportUrl: String = "",
        val examId: Int = 0,
        val isQuiz: Boolean = true,
        val correctProblemCount: Int = 0,
        val time: Int = 0,
        val mainTag: String = "",
        val ranking: Int = 0,
        val wrongAnswerMessage: String = "",
        val isPerfectScore: Boolean = false,
        val requirementQuestion: String = "",
        val requirementPlaceholder: String = "",
        val timer: Int = 0,
        val originalExamId: Int = 0,
        val nickname: String = "",
        val thumbnailUrl: String = "",
    ) : ExamResultState()

    data class Error(
        val exception: Throwable,
    ) : ExamResultState()
}
