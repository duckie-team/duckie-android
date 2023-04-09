/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.model.User

@Immutable
data class ProfileExam(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val solvedCount: Int?,
    val heartCount: Int?,
    val user: User?,
) {
    companion object {
        /**
         * ProfileExam 의 Empty Model 을 제공합니다.
         * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
         */
        fun empty() = ProfileExam(
            id = 0,
            title = "",
            thumbnailUrl = "",
            solvedCount = null,
            heartCount = null,
            user = User.empty(),
        )
    }
}
