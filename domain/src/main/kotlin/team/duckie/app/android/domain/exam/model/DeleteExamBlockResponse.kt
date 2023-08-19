/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteExamBlockResponse(
    @field:JsonProperty("success")
    val success: Boolean,
)
