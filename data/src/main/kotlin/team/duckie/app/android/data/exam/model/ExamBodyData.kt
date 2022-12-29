/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class ExamBodyData(
    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("description")
    val description: String? = null,

    @field:JsonProperty("mainTagId")
    val mainTagId: Int? = null,

    @field:JsonProperty("subTagIds")
    val subTagIds: List<Int>? = null,

    @field:JsonProperty("categoryId")
    val categoryId: Int? = null,

    @field:JsonProperty("certifyingStatement")
    val certifyingStatement: String? = null,

    @field:JsonProperty("thumbnailImageUrl")
    val thumbnailImageUrl: String? = null,

    @field:JsonProperty("thumbnailType")
    val thumbnailType: String? = null,

    @field:JsonProperty("problems")
    val problems: List<ProblemData>? = null,

    @field:JsonProperty("isPublic")
    val isPublic: Boolean? = null,

    @field:JsonProperty("buttonTitle")
    val buttonTitle: String? = null,

    @field:JsonProperty("userId")
    val userId: Int? = null,
)
