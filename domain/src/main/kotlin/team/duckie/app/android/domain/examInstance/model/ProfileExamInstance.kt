/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.user.model.User

@Immutable
data class ProfileExamInstance(
    val id: Int,
    val exam: ProfileExam?,
) {
    companion object {
        /*
         * ProfileExamInstance 의 Empty Model 을 제공합니다.
         * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
         * */
        fun empty() = ProfileExamInstance(
            id = 0,
            exam = ProfileExam(
                id = 0,
                title = "",
                thumbnailUrl = "",
                solvedCount = null,
                heartCount = null,
                user = User.empty(),
            ),
        )
    }
}
