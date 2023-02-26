/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.me.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import team.duckie.app.android.data.auth.datasource.AuthDataSource
import team.duckie.app.android.data.user.datasource.UserDataSource
import team.duckie.app.android.domain.me.MeRepository
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.util.kotlin.exception.ExceptionCode.ClientMeIdNull
import team.duckie.app.android.util.kotlin.exception.ExceptionCode.ClientMeTokenNull
import team.duckie.app.android.util.kotlin.exception.ExceptionCode.ServerUserIdStrange
import team.duckie.app.android.util.kotlin.exception.duckieClientLogicProblemException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
    private val dataStore: DataStore<Preferences>,
) : MeRepository {
    private var me: User? = null

    override suspend fun getMe(): User {
        // 1. 토큰 값이 등록되어 있는지 먼저 확인한다 (이게 없으면 유저 정보 가져오는 거 자체가 안됨)
        val meToken = getMeToken() ?: duckieClientLogicProblemException(code = ClientMeTokenNull)

        // 2. 토큰이 있다면 토큰 검증한다.
        val accessTokenValid = authDataSource.checkAccessToken(meToken).userId > 0

        if (accessTokenValid) {
            // 3. id 값이 등록되어 있는지 확인한다.
            val meId = getMeId() ?: duckieClientLogicProblemException(code = ClientMeIdNull)

            // 4. me 객체값이 있는지 확인한다
            return me ?: kotlin.run {
                // 5. accessToken 관련 설정
                authDataSource.attachAccessTokenToHeader(meToken)

                // 6. me 객체가 없다면, id 기반으로 유저 정보를 가져온 후 setMe 를 통해 설정 뒤 반환한다.
                val user = userDataSource.get(meId)
                setMe(user)

                return user
            }
        } else {
            duckieClientLogicProblemException(code = ServerUserIdStrange)
        }
    }

    override suspend fun setMe(newMe: User) {
        me = newMe
    }

    private suspend fun getMeId(): Int? {
        // TODO(riflockle7): 더 좋은 구현 방법이 있을까?
        // ref: https://medium.com/androiddevelopers/datastore-and-synchronous-work-576f3869ec4c
        return dataStore.data.first()[PreferenceKey.User.Id]?.toInt()
    }

    private suspend fun getMeToken(): String? {
        // TODO(riflockle7): 더 좋은 구현 방법이 있을까?
        // ref: https://medium.com/androiddevelopers/datastore-and-synchronous-work-576f3869ec4c
        return dataStore.data.first()[PreferenceKey.Account.AccessToken]
    }
}
