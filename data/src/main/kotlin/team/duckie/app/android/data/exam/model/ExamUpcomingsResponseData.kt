/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.home.model.HomeFundingData

internal data class ExamUncomingsResponseData(
    @field:JsonProperty("exams")
    val exams: List<HomeFundingData>? = null,

    @field:JsonProperty("page")
    val page: Int? = null,
)
