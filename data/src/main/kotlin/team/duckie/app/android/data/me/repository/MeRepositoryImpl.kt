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
import team.duckie.app.android.common.kotlin.exception.ExceptionCode.ClientMeTokenNull
import team.duckie.app.android.common.kotlin.exception.ExceptionCode.ServerUserIdStrange
import team.duckie.app.android.common.kotlin.exception.duckieClientLogicProblemException
import team.duckie.app.android.core.datastore.PreferenceKey
import team.duckie.app.android.data.auth.datasource.AuthDataSource
import team.duckie.app.android.data.devMode.datasource.DevModeDataSource
import team.duckie.app.android.data.user.datasource.UserDataSource
import team.duckie.app.android.domain.me.MeRepository
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

// TODO(riflockle7): 추후 앱 크래시 데이터 복구용으로 네이밍 변경 필요
class MeRepositoryImpl @Inject constructor(
    private val devModeDataSource: DevModeDataSource,
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
    private val dataStore: DataStore<Preferences>,
) : MeRepository {
    private var isStageChecked: Boolean = false
    private var isProceedEnabled: Boolean? = null
    private var me: User? = null
    override suspend fun getMe(): User {
        return me ?: checkAndGetMe()
    }

    private suspend fun checkAndGetMe(): User {
        // 0. DevMode 에서 API 환경 설정
        if (!isStageChecked) {
            isStageChecked = true
            devModeDataSource.setApiEnvironment(getIsStage())
        }

        // 1. 피처 플래그 갱신
        featureFlagCheck()

        // 2. DataStore 에 토큰 값이 있는지 체크
        val meToken = getMeToken() ?: duckieClientLogicProblemException(code = ClientMeTokenNull)

        // 3. 토큰 유효성 검사, 앱 최소버전을 맞추지 않으면 예외 발생
        val accessTokenCheckResponse = authDataSource.checkAccessToken(meToken)
        val accessTokenValid = accessTokenCheckResponse.userId > 0

        if (accessTokenValid) {
            // 4. me 객체값이 초기화 되었는지 확인
            return me ?: kotlin.run {
                // 5. accessToken 관련 설정
                authDataSource.attachAccessTokenToHeader(meToken)

                // 6. id 기반으로 User 가져온 뒤 앱 내에 User 값 설정
                val user = userDataSource.get(accessTokenCheckResponse.userId)
                setMe(user)

                // 7. User 값 반환
                // 8. user.status = NEW 케이스 처리는 각 화면에서 처리할 것 (일단은)
                return user
            }
        } else {
            duckieClientLogicProblemException(code = ServerUserIdStrange)
        }
    }

    /** featureFlag 값을 체크하여, 각 플래그 항목들을 갱신한다. */
    private suspend fun featureFlagCheck() {
        isProceedEnabled = if (isProceedEnabled == null) {
            dataStore.data.first()[PreferenceKey.FeatureFlag.IsProceedEnable] ?: false
        } else {
            false
        }
    }

    override suspend fun setMe(newMe: User) {
        me = newMe
    }

    override suspend fun getTokenValid(): Boolean {
        val meToken = getMeToken() ?: return false

        return authDataSource.checkAccessToken(meToken).userId > 0
    }

    override suspend fun clearMeToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKey.Account.AccessToken)
        }
    }

    private suspend fun getMeToken(): String? {
        // TODO(riflockle7): 더 좋은 구현 방법이 있을까?
        // ref: https://medium.com/androiddevelopers/datastore-and-synchronous-work-576f3869ec4c
        return dataStore.data.first()[PreferenceKey.Account.AccessToken]
    }

    override suspend fun getIsStage(): Boolean {
        // TODO(riflockle7): 더 좋은 구현 방법이 있을까?
        // ref: https://medium.com/androiddevelopers/datastore-and-synchronous-work-576f3869ec4c
        return dataStore.data.first()[PreferenceKey.DevMode.IsStage] ?: false
    }

    override suspend fun getIsProceedEnable(): Boolean {
        // TODO(riflockle7): 더 좋은 구현 방법이 있을까?
        // ref: https://medium.com/androiddevelopers/datastore-and-synchronous-work-576f3869ec4c
        return dataStore.data.first()[PreferenceKey.FeatureFlag.IsProceedEnable] ?: false
    }
}
