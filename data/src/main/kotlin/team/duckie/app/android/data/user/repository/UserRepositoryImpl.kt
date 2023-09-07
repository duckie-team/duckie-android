/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.repository

import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.ExperimentalApi
import team.duckie.app.android.data.user.datasource.UserDataSource
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.IgnoreUser
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun get(id: Int): User {
        return userDataSource.get(id)
    }

    override suspend fun update(
        id: Int,
        categories: List<Category>?,
        tags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
        introduction: String?,
        status: String?,
    ): User {
        return userDataSource.update(id, categories, tags, profileImageUrl, nickname, introduction, status)
    }

    override suspend fun nicknameValidateCheck(nickname: String): Boolean {
        return userDataSource.nicknameValidateCheck(nickname)
    }

    @AllowMagicNumber
    @ExperimentalApi
    override suspend fun fetchRecommendUserFollowing(userId: Int): UserFollowings {
        return userDataSource.fetchRecommendUserFollowing(userId)
    }

    override suspend fun fetchMeFollowers(): List<User> {
        return userDataSource.fetchMeFollowers()
    }

    override suspend fun fetchMeFollowings(): List<User> {
        return userDataSource.fetchMeFollowings()
    }

    override suspend fun fetchUserProfile(userId: Int): UserProfile {
        return userDataSource.fetchUserProfile(userId)
    }

    override suspend fun fetchUserFollowings(userId: Int): List<User> {
        return userDataSource.fetchUserFollowings(userId)
    }

    override suspend fun fetchUserFollowers(userId: Int): List<User> {
        return userDataSource.fetchUserFollowers(userId)
    }

    override suspend fun fetchIgnoreUsers(): List<IgnoreUser> {
        return userDataSource.fetchIgnoreUsers()
    }
}
