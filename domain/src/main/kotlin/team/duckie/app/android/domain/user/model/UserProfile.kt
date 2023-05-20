/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance

@Immutable
data class UserProfile(
    val followerCount: Int,
    val followingCount: Int,
    val createdExams: List<ProfileExam>?,
    val solvedExamInstances: List<ProfileExamInstance>?,
    val heartExams: List<ProfileExam>?,
    val user: User?,
) {
    companion object {
        /*
       * UserProfile 의 Empty Model 을 제공합니다.
       * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
       * */
        fun empty() = UserProfile(
            followerCount = 0,
            followingCount = 0,
            createdExams = null,
            solvedExamInstances = null,
            heartExams = null,
            user = User.empty(),
        )

        fun UserProfile.username(): String =
            this.user?.nickname ?: ""
    }
}
