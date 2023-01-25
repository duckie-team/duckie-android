/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("ConstPropertyName")

package team.duckie.app.android.data.user.mapper

import kotlin.random.Random
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
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.fastMap

private const val NicknameSuffixMaxLength = 10_000
private const val NicknameSuffixLength = 4

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName
        ?: "덕키즈_${Random.nextInt(NicknameSuffixMaxLength).toString().padStart(NicknameSuffixLength, '0')}",
    profileImageUrl = profileImageUrl ?: duckieResponseFieldNpe("profileImageUrl"),
    tier = DuckieTier.values().toList().fastFirstOrNull { it.level == tier } ?: DuckieTier.DuckKid,
    favoriteTags = favoriteTags?.fastMap(TagData::toDomain)?.toImmutableList() ?: persistentListOf(),
    favoriteCategories = favoriteCategories?.fastMap(CategoryData::toDomain)?.toImmutableList()
        ?: persistentListOf(),
)
