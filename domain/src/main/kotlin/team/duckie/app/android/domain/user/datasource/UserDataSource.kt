/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.datasource

import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowings

interface UserDataSource {
    // TODO(riflockle7): MeRepository, MeDataSource 만들면서 없어질 예정
    suspend fun getMe(): User?

    // TODO(riflockle7): MeRepository, MeDataSource 만들면서 없어질 예정
    suspend fun setMe(newMe: User)

    suspend fun get(id: Int): User

    suspend fun update(
        id: Int,
        categories: List<Category>?,
        tags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
        status: String?,
    ): User

    suspend fun nicknameValidateCheck(nickname: String): Boolean

    suspend fun fetchUserFollowing(userId: Int): UserFollowings

    suspend fun fetchMeFollowers(): List<User>

    suspend fun fetchMeFollowings(): List<User>
}
