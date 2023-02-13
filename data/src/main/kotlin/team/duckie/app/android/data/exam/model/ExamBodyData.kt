/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

internal data class ExamBodyData(
    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("description")
    val description: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("thumbnailType")
    val thumbnailType: String? = null,

    @field:JsonProperty("mainTagId")
    val mainTagId: Int? = null,

    @field:JsonProperty("categoryId")
    val categoryId: Int? = null,

    @field:JsonProperty("subTagIds")
    val subTagIds: List<Int>? = null,

    @field:JsonProperty("certifyingStatement")
    val certifyingStatement: String? = null,

    @field:JsonProperty("buttonTitle")
    val buttonTitle: String? = null,

    @field:JsonProperty("problems")
    val problems: List<ProblemData>? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:JsonProperty("status")
    val status: String? = null,
)

internal data class ExamInstanceBodyData(
    @field:JsonProperty("examId")
    val examId: Int? = null,
)

internal data class ExamInstanceSubmitBodyData(
    @field:JsonProperty("submitted")
    val submitted: List<String>? = null,
)
