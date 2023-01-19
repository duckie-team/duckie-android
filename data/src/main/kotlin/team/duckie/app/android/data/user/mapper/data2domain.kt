/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.user.constant.DuckieTier
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName ?: duckieResponseFieldNpe("nickName"),
    profileImageUrl = profileImageUrl ?: duckieResponseFieldNpe("profileImageUrl"),
    tier = tier?.let { DuckieTier.values()[it] } ?: duckieResponseFieldNpe("tier"),
    favoriteTags = favoriteTags?.filterNotNull()?.toImmutableList(),
    favoriteCategories = favoriteCategories?.filterNotNull()?.toImmutableList(),
)
