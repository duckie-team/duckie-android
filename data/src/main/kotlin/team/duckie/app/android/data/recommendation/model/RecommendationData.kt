/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ExamData

internal data class RecommendationData(
    @JsonProperty("jumbotrons")
    val jumbotrons: List<ExamData>? = null,

    @JsonProperty("recommendations")
    val recommendations: List<RecommendationItemData>? = null,

    @JsonProperty("page")
    val page: Int? = null,
)
