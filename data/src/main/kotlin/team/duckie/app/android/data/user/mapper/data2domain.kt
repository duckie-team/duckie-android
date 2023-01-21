/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.mapper

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.user.constant.DuckieTier
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.fastFirstOrNull

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName ?: duckieResponseFieldNpe("nickName"),
    profileImageUrl = profileImageUrl ?: duckieResponseFieldNpe("profileImageUrl"),
    tier = DuckieTier.values().toList().fastFirstOrNull { it.level == tier } ?: DuckieTier.DuckKid,
    favoriteTags = favoriteTags?.fastMap(TagData::toDomain)?.toImmutableList() ?: persistentListOf(),
    favoriteCategories = favoriteCategories?.fastMap(CategoryData::toDomain)?.toImmutableList()
        ?: persistentListOf(),
)
