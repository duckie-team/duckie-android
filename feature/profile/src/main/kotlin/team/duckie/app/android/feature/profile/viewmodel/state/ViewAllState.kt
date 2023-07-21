/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.viewmodel.state

sealed class ViewAllState {
    object Loading : ViewAllState()

    data class Success(
        val userId: Int,
        val examType: ExamType,
    ) : ViewAllState()

    class Error(val exception: Throwable) : ViewAllState()
}

enum class ExamType {
    Heart,
    Created,
    Solved,
}
