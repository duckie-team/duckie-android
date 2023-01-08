/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.search.model.Search

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SearchData.ExamSearchData::class, name = Search.Exam),
    JsonSubTypes.Type(value = SearchData.UserSearchData::class, name = Search.User),
)
internal sealed class SearchData(
    @field:JsonProperty("type")
    open val type: String? = null,

    @field:JsonProperty("page")
    open val page: Int? = null,
) {
    internal data class ExamSearchData(
        @field:JsonProperty("exams")
        val exams: List<ExamData>? = null,
        override val type: String? = null,
        override val page: Int? = null,
    ) : SearchData()

    internal data class UserSearchData(
        @field:JsonProperty("users")
        val users: List<UserResponse>? = null,
        override val type: String? = null,
        override val page: Int? = null,
    ) : SearchData()
}
