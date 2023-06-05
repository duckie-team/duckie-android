/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.auth.mapper

import team.duckie.app.android.data.auth.model.AccessTokenCheckResponseData
import team.duckie.app.android.data.auth.model.AuthJoinResponseData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.auth.model.AccessTokenCheckResponse
import team.duckie.app.android.domain.auth.model.JoinResponse
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe

internal fun AuthJoinResponseData.toDomain() = JoinResponse(
    isNewUser = isNewUser ?: duckieResponseFieldNpe("isNewUser"),
    accessToken = accessToken ?: duckieResponseFieldNpe("accessToken"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("user"),
)

internal fun AccessTokenCheckResponseData.toDomain() = AccessTokenCheckResponse(
    userId = userId ?: duckieResponseFieldNpe("userId"),
    type = type ?: duckieResponseFieldNpe("type"),
)
