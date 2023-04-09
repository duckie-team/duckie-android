/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ProfileExamData
import team.duckie.app.android.data.examInstance.model.ProfileExamInstanceData

data class UserProfileData(
    @field:JsonProperty("followerCount")
    val followerCount: Int? = null,
    @field:JsonProperty("followingCount")
    val followingCount: Int? = null,
    @field:JsonProperty("createdExams")
    val createdExams: List<ProfileExamData>? = null,
    @field:JsonProperty("solvedExamInstances")
    val solvedExamInstances: List<ProfileExamInstanceData>? = null,
    @field:JsonProperty("heartExams")
    val heartExams: List<ProfileExamData>? = null,
    @field:JsonProperty("user")
    val user: UserResponse? = null,
)
