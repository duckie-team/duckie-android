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
internal class SearchData(
    @field:JsonProperty("type")
    val type: String? = null,

    @field:JsonProperty("result")
    val result: SearchResultData? = null,
)

internal class SearchResultData(
    @field:JsonProperty("exams")
    val exams: List<ExamData>? = null,

    @field:JsonProperty("users")
    val users: List<UserResponse>? = null,

    @field:JsonProperty("tags")
    val tags: List<TagData>? = null,
)
