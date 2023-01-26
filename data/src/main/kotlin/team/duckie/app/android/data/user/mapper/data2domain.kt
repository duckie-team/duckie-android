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
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.user.model.DuckPowerResponse
import team.duckie.app.android.data.user.model.UserFollowingResponse
import team.duckie.app.android.data.user.model.UserFollowingRecommendationsResponse
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.user.model.DuckPower
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.model.UserFollowingRecommendations
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    nickname = nickName ?: duckieResponseFieldNpe("${this::class.java.simpleName}/nickName"),
    profileImageUrl = profileImageUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.profileImageUrl"),
    duckPower = duckPower?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.duckPower"),
    favoriteTags = favoriteTags?.filterNotNull()?.toImmutableList() ?: persistentListOf(),
    favoriteCategories = favoriteCategories?.filterNotNull()?.toImmutableList() ?: persistentListOf(),
)

internal fun UserFollowingRecommendationsResponse.toDomain() = UserFollowingRecommendations(
    category = category?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.category"),
    user = user?.fastMap(UserResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
)

internal fun UserFollowingResponse.toDomain() = UserFollowing(
    followingRecommendations = followingRecommendations?.fastMap(UserFollowingRecommendationsResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.followingRecommendations")
)

internal fun DuckPowerResponse.toDomain() = DuckPower(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    tier = tier ?: duckieResponseFieldNpe("${this::class.java.simpleName}.tier"),
    tag = tag?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.tag"),
)
