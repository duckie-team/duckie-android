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

@Immutable
data class RecommendationItem(
    val id: Int,
    val title: String,
    val tag: Tag?,
    val exams: List<Exam>,
)
