/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

data class ExamMeBlocksResponse(
    @field:JsonProperty("exams") val exams: List<ExamBlockDetailResponse>?,
)

data class ExamBlockDetailResponse(
    @field:JsonProperty("id") val id: Int?,
    @field:JsonProperty("title") val title: String?,
    @field:JsonProperty("thumbnailUrl") val thumbnailUrl: String?,
    @field:JsonProperty("user") val user: UserResponse?,
    @field:JsonProperty("examBlock") val examBlock: ExamBlockResponse?,
)

data class ExamBlockResponse(
    @field:JsonProperty("id") val id: Int?,
)
