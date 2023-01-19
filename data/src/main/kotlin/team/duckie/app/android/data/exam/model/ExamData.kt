/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.UserResponse

internal data class ExamData(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("description")
    val description: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("type")
    val type: String? = null,

    @field:JsonProperty("buttonTitle")
    val buttonTitle: String? = null,

    @field:JsonProperty("certifyingStatement")
    val certifyingStatement: String? = null,

    @field:JsonProperty("solvedCount")
    val solvedCount: Int? = null,

    @field:JsonProperty("answerRate")
    val answerRate: Float? = null,

    @field:JsonProperty("user")
    val user: UserResponse? = null,

    @field:JsonProperty("category")
    val category: CategoryData? = null,

    @field:JsonProperty("mainTag")
    val mainTag: TagData? = null,

    @field:JsonProperty("subTags")
    val subTags: List<TagData>? = null,

    @field:JsonProperty("problems")
    val problems: List<ProblemData>? = null,
)
