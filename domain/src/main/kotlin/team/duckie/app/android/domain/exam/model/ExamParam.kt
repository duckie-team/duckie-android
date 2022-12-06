/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

data class ExamParam(
    val title: String,
    val description: String,
    val mainTag: Tag,
    val subTag: List<Tag>?,
    val certifyingStatement: String,
    val thumbnailImageUrl: String?,
    val thumbnailType: String,
    val problems: ProblemItem,
    val isPublic: Boolean?,
    val buttonText: String?,
)
