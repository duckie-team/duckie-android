/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ExamRequest(
    @field:JsonProperty("title")
    val title: String,
    @field:JsonProperty("description")
    val description: String,
    @field:JsonProperty("mainTag")
    val mainTag: TagData,
    @field:JsonProperty("subTags")
    val subTag: List<TagData>?,
    @field:JsonProperty("userId")
    val userId: Int,
    @field:JsonProperty("certifyingStatement")
    val certifyingStatement: String,
    @field:JsonProperty("thumbnailImageUrl")
    val thumbnailImageUrl: String?,
    @field:JsonProperty("thumbnailType")
    val thumbnailType: String,
    @field:JsonProperty("problems")
    val problems: ProblemItemData,
    @field:JsonProperty("isPublic")
    val isPublic: Boolean?,
    @field:JsonProperty("buttonText")
    val buttonTitle: String?,
)
