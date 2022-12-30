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

@OutOfDateApi
@Immutable
data class Exam(
    val title: String,
    val description: String,
    val thumbnailUrl: String?,
    val buttonTitle: String,
    val certifyingStatement: String,
    val solvedCount: Int,
    val isPublic: Boolean,
    val mainTag: Int,
    val subTags: ImmutableList<Int>,
)
