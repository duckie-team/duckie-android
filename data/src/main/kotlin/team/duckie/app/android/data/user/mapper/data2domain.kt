/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("ConstPropertyName", "PrivatePropertyName")

package team.duckie.app.android.data.user.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ProfileExamData
import team.duckie.app.android.data.examInstance.model.ProfileExamInstanceData
import team.duckie.app.android.data.follow.mapper.toDomain
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.DuckPowerResponse
import team.duckie.app.android.data.user.model.UserFollowingResponse
import team.duckie.app.android.data.user.model.UserFollowingsResponse
import team.duckie.app.android.data.user.model.UserProfileData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.data.user.model.UsersResponse
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.domain.user.model.DuckPower
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.model.UserFollowings
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.domain.user.model.toUserAuthStatus
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import kotlin.random.Random

private const val NicknameSuffixMaxLength = 10_000
private const val NicknameSuffixLength = 4
private val DuckieDefaultNickname: String
    get() {
        val suffix = Random.nextInt(NicknameSuffixMaxLength)
            .toString()
            .padStart(NicknameSuffixLength, '0')
        return "덕키즈_$suffix"
    }

internal fun UserResponse.toDomain() = User(
    id = id ?: duckieResponseFieldNpe("id"),
    nickname = nickName ?: DuckieDefaultNickname,
    profileImageUrl = profileImageUrl,
    introduction = introduction,
    status = status?.toUserAuthStatus,
    duckPower = duckPower?.toDomain(),
    follow = follow?.toDomain(),
    favoriteTags = favoriteTags?.fastMap(TagData::toDomain)?.toImmutableList(),
    favoriteCategories = favoriteCategories?.fastMap(CategoryData::toDomain)?.toImmutableList(),
    permissions = permissions,
)

internal fun UsersResponse.toDomain() = users?.fastMap { user -> user.toDomain() }
    ?: duckieResponseFieldNpe("${this::class.java.simpleName}.users")

internal fun UserFollowingResponse.toDomain() = UserFollowing(
    category = category?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.category"),
    users = users?.fastMap(UserResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
)

internal fun UserFollowingsResponse.toDomain() = UserFollowings(
    followingRecommendations = userRecommendations?.fastMap(UserFollowingResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.followingRecommendations"),
)

internal fun DuckPowerResponse.toDomain(): DuckPower {
    return DuckPower(
        id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
        tier = tier ?: duckieResponseFieldNpe("${this::class.java.simpleName}.tier"),
        tag = tag?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.tag"),
    )
}

internal fun ProfileExamInstanceData.toDomain() = ProfileExamInstance(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    exam = exam?.toDomain(),
)

internal fun UserProfileData.toDomain() = UserProfile(
    followerCount = followerCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.followerCount"),
    followingCount = followingCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.followingCount"),
    createdExams = createdExams?.map(ProfileExamData::toDomain),
    solvedExamInstances = solvedExamInstances?.map(ProfileExamInstanceData::toDomain),
    heartExams = heartExams?.map(ProfileExamData::toDomain),
    user = user?.toDomain(),
)
