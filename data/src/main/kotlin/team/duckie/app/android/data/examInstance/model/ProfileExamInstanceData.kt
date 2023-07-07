/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.examInstance.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ProfileExamData

data class ProfileExamInstanceData(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("exam")
    val exam: ProfileExamData? = null,
)

data class ProfileExamInstanceDatas(
    @field:JsonProperty("examInstances")
    val examInstances: List<ProfileExamInstanceData>? = null,

    @field:JsonProperty("page")
    val page: Int? = null,
)
