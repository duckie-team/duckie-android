/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.problem.model


import com.fasterxml.jackson.annotation.JsonProperty

internal data class PatchProblemBody(
    @JsonProperty("answer")
    val status: String?,

    @JsonProperty("correctAnswer")
    val isSample: Boolean?,
)
