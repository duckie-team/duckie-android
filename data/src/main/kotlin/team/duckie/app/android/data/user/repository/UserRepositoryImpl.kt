/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.user.repository

import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.datasource.UserDataSource
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings
import team.duckie.app.android.domain.user.repository.UserRepository
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.ExperimentalApi
import javax.inject.Inject
import javax.inject.Singleton

// TODO(riflockle7): 결국 User 와 token 은 엮일 수 밖에 없는 요소이다.
//  하지만 기능상 각 Repository 로 분리되는 것도 맞다.
//  그래서 UseCase 로 AuthRepository 를 호출해주었는데 이는 괜찮은 걸까?
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    private var me: User? = null

    // TODO(riflockle7): 로그인 화면으로 넘겨주는 flow 필요 (throw Error 할지 고민 중)
    override suspend fun getMe(): User? {
        return userDataSource.getMe()
    }

    override suspend fun setMe(newMe: User) {
        return userDataSource.setMe(newMe)
    }

    override suspend fun get(id: Int): User {
        return userDataSource.get(id)
    }

    override suspend fun update(
        id: Int,
        categories: List<Category>?,
        tags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
        status: String?,
    ): User {
        return userDataSource.update(id, categories, tags, profileImageUrl, nickname, status)
    }

    override suspend fun nicknameValidateCheck(nickname: String): Boolean {
        return userDataSource.nicknameValidateCheck(nickname)
    }

    // TODO(riflockle7): GET /users/following API commit (변경점이 많아 TODO 적음)
    @AllowMagicNumber
    @ExperimentalApi
    override suspend fun fetchUserFollowing(userId: Int): UserFollowings {
        return userDataSource.fetchUserFollowing(userId)
    }

    override suspend fun fetchMeFollowers(): List<User> {
        return userDataSource.fetchMeFollowers()
    }

    override suspend fun fetchMeFollowings(): List<User> {
        return userDataSource.fetchMeFollowings()
    }
}
