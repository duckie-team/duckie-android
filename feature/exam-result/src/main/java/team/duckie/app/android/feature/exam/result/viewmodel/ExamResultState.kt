/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

sealed class ExamResultState {
    object Loading : ExamResultState()

    data class Success(
        val reportUrl: String = "",
        val isQuiz: Boolean = true,
        val correctProblemCount: Int = 0,
        val time: Int = 0,
        val mainTag: String = "",
        val rank: Int = 0,
    ) : ExamResultState()

    data class Error(
        val exception: Throwable,
    ) : ExamResultState()
}
