/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.auth.datasource

import team.duckie.app.android.domain.auth.model.AccessTokenCheckResponse
import team.duckie.app.android.domain.auth.model.JoinResponse

interface AuthDataSource {
    suspend fun join(kakaoAccessToken: String): JoinResponse

    suspend fun checkAccessToken(token: String): AccessTokenCheckResponse

    fun attachAccessTokenToHeader(accessToken: String)
}
