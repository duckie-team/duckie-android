/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.follow.mapper

import team.duckie.app.android.data.follow.model.FollowsBodyData
import team.duckie.app.android.domain.follow.model.FollowBody

internal fun FollowBody.toData() = FollowsBodyData(userId = userId, followingId = followingId)
