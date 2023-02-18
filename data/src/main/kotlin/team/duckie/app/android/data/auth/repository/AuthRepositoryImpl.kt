/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.auth.repository

import team.duckie.app.android.domain.auth.datasource.AuthDataSource
import team.duckie.app.android.domain.auth.model.AccessTokenCheckResponse
import team.duckie.app.android.domain.auth.model.JoinResponse
import team.duckie.app.android.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun join(kakaoAccessToken: String): JoinResponse {
        return authDataSource.join(kakaoAccessToken)
    }

    override suspend fun checkAccessToken(token: String): AccessTokenCheckResponse {
        return authDataSource.checkAccessToken(token)
    }

    override fun attachAccessTokenToHeader(accessToken: String) {
        return attachAccessTokenToHeader(accessToken)
    }
}
