/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import team.duckie.app.android.util.kotlin.OutOfDateApi

@OptIn(OutOfDateApi::class)
data class ExamMeFollowingResponse(
    val exams: List<Exam>,
    val page: Int,
)
