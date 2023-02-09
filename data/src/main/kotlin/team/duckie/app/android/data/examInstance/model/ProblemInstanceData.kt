/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.examInstance.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.ProblemData

internal data class ProblemInstanceData(
    @JsonProperty("message")
    val id: Int? = null,

    @JsonProperty("problem")
    val problem: ProblemData? = null,

    @JsonProperty("status")
    val status: String? = null,

    @JsonProperty("submittedAnswer")
    val submittedAnswer: String? = null,
)
