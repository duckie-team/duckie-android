/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

data class ProfileExamData(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("solvedCount")
    val solvedCount: Int? = null,

    @field:JsonProperty("heartCount")
    val heartCount: Int? = null,

    @field:JsonProperty("user")
    val user: UserResponse? = null,

    @field:JsonProperty("problemSolvedCount")
    val problemSolvedCount: Int? = null,

    @field:JsonProperty("totalProblemCount")
    val totalProblemCount: Int? = null,
)

data class ProfileExamDatas(
    @field:JsonProperty("exams")
    val exams: List<ProfileExamData>? = null,
    @field:JsonProperty("page")
    val page: Int? = null,
)
