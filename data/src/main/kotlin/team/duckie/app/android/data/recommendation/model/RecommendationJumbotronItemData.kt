/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.tag.model.TagData

internal data class RecommendationJumbotronItemData(
    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @JsonProperty("buttonTitle")
    val buttonTitle: String? = null,

    @JsonProperty("type")
    val type: String? = null,

    @JsonProperty("certifyingStatement")
    val certifyingStatement: String? = null,

    @JsonProperty("solvedCount")
    val solvedCount: Int? = null,

    @JsonProperty("answerRate")
    val answerRate: Float? = null,

    @JsonProperty("category")
    val category: CategoryData? = null,

    @JsonProperty("mainTag")
    val mainTag: TagData? = null,

    @JsonProperty("subTags")
    val subTags: List<TagData>? = null,
)
