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
import team.duckie.app.android.data.exam.mapper.toDomainForNonProfile
import team.duckie.app.android.data.user.model.UserFollowingData
import team.duckie.app.android.data.user.model.UserFollowingRecommendationsData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.user.constant.DuckieTier
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.model.UserFollowingRecommendations
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName ?: duckieResponseFieldNpe("nickName"),
    profileImageUrl = profileImageUrl ?: duckieResponseFieldNpe("profileImageUrl"),
    tier = tier?.let { DuckieTier.values()[it] } ?: duckieResponseFieldNpe("tier"),
    favoriteTags = favoriteTags?.filterNotNull()?.toImmutableList() ?: duckieResponseFieldNpe("favoriteTags"),
    favoriteCategories = favoriteCategories?.filterNotNull()?.toImmutableList()
        ?: duckieResponseFieldNpe("favoriteCategories"),
)

internal fun UserResponse.toDomainForNonProfile() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName ?: duckieResponseFieldNpe("nickName"),
    profileImageUrl = profileImageUrl ?: "",
    tier = tier?.let { DuckieTier.values()[it] } ?: DuckieTier.DuckKid,
    favoriteTags = favoriteTags?.filterNotNull()?.toImmutableList() ?: persistentListOf(),
    favoriteCategories = favoriteCategories?.filterNotNull()?.toImmutableList()
        ?: persistentListOf()
)

internal fun UserFollowingRecommendationsData.toDomain() = UserFollowingRecommendations(
    category = category?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.category"),
    user = user?.fastMap(UserResponse::toDomain) ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
)

internal fun UserFollowingData.toDomain() = UserFollowing(
    followingRecommendations = followingRecommendations?.fastMap(UserFollowingRecommendationsData::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.followingRecommendations")
)
