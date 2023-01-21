/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.follow.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.follow.model.FollowBody

@Immutable
interface FollowsRepository {
    suspend fun follow(followsBody: FollowBody): Boolean

    suspend fun unFollow(followsBody: FollowBody): Boolean
}
