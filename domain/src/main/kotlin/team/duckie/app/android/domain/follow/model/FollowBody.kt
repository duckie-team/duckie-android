/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.follow.model

import androidx.compose.runtime.Immutable

// TODO(riflockle7): value class 적용 검토하기
@Immutable
data class FollowBody(
    val followingId: Int,
)
