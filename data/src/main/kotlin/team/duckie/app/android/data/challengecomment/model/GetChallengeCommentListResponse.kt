/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.challengecomment.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GetChallengeCommentListResponse(
    @field:JsonProperty("total")
    val total: Int ?= null,
    @field:JsonProperty("data")
    val data: List<ChallengeCommentResponse> ?= null,
    @field:JsonProperty("page")
    val page: Int ?= null,
)
