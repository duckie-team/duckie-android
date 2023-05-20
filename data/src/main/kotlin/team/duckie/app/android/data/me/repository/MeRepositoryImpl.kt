/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.me.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import team.duckie.app.android.data.auth.datasource.AuthDataSource
import team.duckie.app.android.data.user.datasource.UserDataSource
import team.duckie.app.android.domain.me.MeRepository
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.core.datastore.PreferenceKey
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
        // 1. DataStore 에 토큰 값이 있는지 체크
        val meToken = getMeToken() ?: duckieClientLogicProblemException(code = ClientMeTokenNull)

        // 2. 토큰 검증한다.
        val accessTokenValid = authDataSource.checkAccessToken(meToken).userId > 0

        if (accessTokenValid) {
            // 3. DataStore 에 id 값이 있는지 체크
            val meId = getMeId() ?: duckieClientLogicProblemException(code = ClientMeIdNull)

            // 4. me 객체값이 초기화 되었는지 확인
            return me ?: kotlin.run {
                // 5. accessToken 관련 설정
                authDataSource.attachAccessTokenToHeader(meToken)

                // 6. id 기반으로 User 가져온 뒤 앱 내에 User 값 설정
                val user = userDataSource.get(meId)
                setMe(user)

                // 7. User 값 반환
                // 8. user.status = NEW 케이스 처리는 각 화면에서 처리할 것 (일단은)
                return user
            }
        } else {
            duckieClientLogicProblemException(code = ServerUserIdStrange)
        }
    }

    override suspend fun setMe(newMe: User) {
        me = newMe
    }

    override suspend fun clearMeToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKey.Account.AccessToken)
        }
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
