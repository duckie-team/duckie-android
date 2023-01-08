/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.tag.model.TagData

internal data class RecommendationItemData(
    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("tag")
    val tag: TagData? = null,

    @JsonProperty("exams")
    val exams: List<ExamData>? = null,
)
