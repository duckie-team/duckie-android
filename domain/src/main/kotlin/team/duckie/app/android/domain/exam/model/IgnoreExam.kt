/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import team.duckie.app.android.domain.user.model.User

data class IgnoreExam(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val user: User,
    val examBlock: ExamBlock,
)
