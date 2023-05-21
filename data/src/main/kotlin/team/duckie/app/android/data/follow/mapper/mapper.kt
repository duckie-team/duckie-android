/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.follow.mapper

import team.duckie.app.android.data.follow.model.FollowData
import team.duckie.app.android.data.follow.model.FollowBodyData
import team.duckie.app.android.domain.follow.model.Follow
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe

internal fun FollowData.toDomain() = Follow(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
)

internal fun FollowBody.toData() = FollowBodyData(followingId = followingId)
