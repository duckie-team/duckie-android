/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

data class ExamThumbnailBody(
    val category: String,
    val certifyingStatement: String,
    val mainTag: String,
    val nickName: String,
    val title: String,
    val type: String,
)
