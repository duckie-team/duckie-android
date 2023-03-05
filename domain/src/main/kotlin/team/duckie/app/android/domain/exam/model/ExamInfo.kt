/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable

@Immutable
data class ExamInfo(
    val id: Int,
    val nickname: String,
    val title: String,
    val thumbnailUrl: String,
    val solvedCount: Int?,
)
