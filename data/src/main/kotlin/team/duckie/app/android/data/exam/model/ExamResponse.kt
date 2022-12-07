/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserData
import java.util.Date

data class ExamResponse(
    @field:JsonProperty("title")
    val title: String,
    @field:JsonProperty("description")
    val description: String,
    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String?,
    @field:JsonProperty("certifyingStatement")
    val certifyingStatement: String,
    @field:JsonProperty("buttonTitle")
    val buttonTitle: String,
    @field:JsonProperty("isPublic")
    val isPublic: Boolean,
    @field:JsonProperty("tags")
    val tags: List<TagData>,
    @field:JsonProperty("user")
    val user: UserData,
    @field:JsonProperty("deletedAt")
    val deletedAt: Date? = null,
    @field:JsonProperty("id")
    val id: Int,
    @field:JsonProperty("createdAt")
    val createdAt: Date,
    @field:JsonProperty("updatedAt")
    val updatedAt: Date,
    @field:JsonProperty("solvedCount")
    val solvedCount: Int,
    @field:JsonProperty("answerRate")
    val answerRate: Float,
    @field:JsonProperty("canRetry")
    val canRetry: Boolean,
)
