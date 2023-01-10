/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.util.kotlin.OutOfDateApi

@OptIn(OutOfDateApi::class)
@Immutable
data class RecommendationItem(
    val title: String,
    val tag: Tag,
    val exams: List<Exam>,
)
