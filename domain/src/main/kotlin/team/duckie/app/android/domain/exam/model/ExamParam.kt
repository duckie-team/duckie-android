/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class ExamParam(
    val title: String,
    val description: String,
    val mainTag: Tag,
    val subTag: ImmutableList<Tag> = emptyList<Tag>().toImmutableList(),
    val userId: Int,
    val certifyingStatement: String,
    val thumbnailImageUrl: String?,
    val thumbnailType: String,
    val problems: ProblemItem,
    val isPublic: Boolean?,
    val buttonTitle: String?,
)
