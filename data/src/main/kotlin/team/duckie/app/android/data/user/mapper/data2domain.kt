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
import team.duckie.app.android.data.user.model.DuckPowerResponse
import team.duckie.app.android.data.user.model.UserFollowingRecommendationsResponse
import team.duckie.app.android.data.user.model.UserFollowingResponse
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.DuckPower
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.model.UserFollowingRecommendations
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

private const val NicknameSuffixMaxLength = 10_000
private const val NicknameSuffixLength = 4

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName
        ?: "덕키즈_${Random.nextInt(NicknameSuffixMaxLength).toString().padStart(NicknameSuffixLength, '0')}",
    profileImageUrl = profileImageUrl ?: duckieResponseFieldNpe("profileImageUrl"),
    duckPower = duckPower?.toDomain() ?: DuckPower(),
    favoriteTags = favoriteTags?.fastMap(TagData::toDomain)?.toImmutableList() ?: persistentListOf(),
    favoriteCategories = favoriteCategories?.fastMap(CategoryData::toDomain)?.toImmutableList()
        ?: persistentListOf(),
)

internal fun UserFollowingRecommendationsResponse.toDomain() = UserFollowingRecommendations(
    category = category?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.category"),
    user = user?.fastMap(UserResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
)

internal fun UserFollowingResponse.toDomain() = UserFollowing(
    followingRecommendations = followingRecommendations?.fastMap(UserFollowingRecommendationsResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.followingRecommendations"),
)

internal fun DuckPowerResponse.toDomain() = DuckPower(
    id = id ?: 0,
    tier = tier ?: "",
    tag = tag?.toDomain() ?: Tag(0, ""),
)
