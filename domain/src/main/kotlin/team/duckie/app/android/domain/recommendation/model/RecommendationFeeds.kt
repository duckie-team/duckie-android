/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.exam.model.Exam

@Immutable
data class RecommendationFeeds(
    val jumbotrons: ImmutableList<Exam>?,
    val recommendations: ImmutableList<RecommendationItem>,
    val page: Int?,
)
