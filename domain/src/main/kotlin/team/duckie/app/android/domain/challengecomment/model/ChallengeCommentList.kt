/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.model

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.collections.immutable.ImmutableList

data class ChallengeCommentList(
    val total: Int ?= null,
    val data: ImmutableList<ChallengeComment> ?= null,
)
