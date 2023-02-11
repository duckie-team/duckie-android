/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable

@Immutable
data class ExamThumbnailBody(
    val title: String,
    val mainTag: String,
    val category: String,
    val nickName: String,
    val certifyingStatement: String,
    val type: String,
)
