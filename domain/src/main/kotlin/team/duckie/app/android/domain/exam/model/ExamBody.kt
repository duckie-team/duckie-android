/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.util.kotlin.OutOfDateApi

enum class ThumbnailType(val value: String) {
    Default("default"),
    Image("image"),
}

@OutOfDateApi
@Immutable
data class ExamBody(
    val title: String,
    val description: String,
    val mainTagId: Int,
    val subTagIds: ImmutableList<Int>?,
    val categoryId: Int,
    val certifyingStatement: String,
    val thumbnailImageUrl: String?,
    val thumbnailType: ThumbnailType?,
    val problems: ImmutableList<Problem>,
    val isPublic: Boolean?,
    val buttonTitle: String?,
    val userId: Int,
)
