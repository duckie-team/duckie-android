/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.examInstance.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ExamData

internal data class ExamInstanceData(
    @JsonProperty("id")
    val id: Int? = null,

    @JsonProperty("exam")
    val exam: ExamData? = null,

    @JsonProperty("problemInstances")
    val problemInstances: List<ProblemInstanceData>? = null,

    @JsonProperty("status")
    val status: String? = null,
)
