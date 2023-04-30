/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.viewmodel

sealed class ExamResultState {
    object Loading : ExamResultState()

    data class Success(
        val reportUrl: String = "",
    ) : ExamResultState()

    data class Error(
        val exception: Throwable,
    ) : ExamResultState()
}
