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

@Immutable
enum class ThumbnailType(val value: String) {
    Text("text"),
    Image("image"),
}

@Immutable
data class ExamBody(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val thumbnailType: ThumbnailType,
    val mainTagId: Int,
    val categoryId: Int,
    val subTagIds: ImmutableList<Int>?,
    val certifyingStatement: String,
    val buttonTitle: String,
    val problems: ImmutableList<Problem>,
    val status: String?,
)

@Immutable
data class ExamInstanceBody(
    val examId: Int,
)

@Immutable
data class ExamInstanceSubmitBody(
    val submitted: ImmutableList<String>,
)
