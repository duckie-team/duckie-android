/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.model


import com.fasterxml.jackson.annotation.JsonProperty

internal data class RecommendationData(
    @JsonProperty("jumbotrons")
    val jumbotrons: List<RecommendationJumbotronItemData>? = null,

    @JsonProperty("recommendations")
    val recommendations: List<RecommendationItemData>? = null,

    @JsonProperty("page")
    val page: Int? = null,

    @JsonProperty("offset")
    val offset: Int? = null,

    @JsonProperty("limit")
    val limit: Int? = null,
)
