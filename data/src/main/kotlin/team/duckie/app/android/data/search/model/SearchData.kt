/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.squareup.moshi.JsonClass
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.UserResponse

@JsonClass(generateAdapter = true)
internal sealed class SearchData(
    @field:JsonProperty("type")
    open val type: String? = null,
) {
    @JsonClass(generateAdapter = true)
    internal data class ExamSearchData(
        @field:JsonProperty("result")
        val result: List<ExamData>? = null,
    ) : SearchData()

    @JsonClass(generateAdapter = true)
    internal data class UserSearchData(
        @field:JsonProperty("result")
        val result: List<UserResponse>? = null,
    ) : SearchData()

    @JsonClass(generateAdapter = true)
    internal data class TagSearchData(
        @field:JsonProperty("result")
        val result: List<TagData>? = null,
    ) : SearchData()
}
