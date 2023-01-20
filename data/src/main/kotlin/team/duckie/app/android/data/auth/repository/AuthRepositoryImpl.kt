/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.auth.repository

import com.github.kittinunf.fuel.core.FuelManager
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import javax.inject.Inject
import team.duckie.app.android.data._datasource.DuckieHttpHeaders
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.update
import team.duckie.app.android.data.auth.mapper.toDomain
import team.duckie.app.android.data.auth.model.AccessTokenCheckResponseData
import team.duckie.app.android.data.auth.model.AuthJoinResponseData
import team.duckie.app.android.domain.auth.model.AccessTokenCheckResponse
import team.duckie.app.android.domain.auth.model.JoinResponse
import team.duckie.app.android.domain.auth.repository.AuthRepository
import team.duckie.app.ktor.client.plugin.DuckieAuthorizationHeader

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun join(kakaoAccessToken: String): JoinResponse {
        val response = client.post("/auth/kakao") {
            jsonBody {
                "accessToken" withString kakaoAccessToken
            }
        }
        return responseCatching(
            response = response.body(),
            parse = AuthJoinResponseData::toDomain,
        )
    }

    override suspend fun checkAccessToken(token: String): AccessTokenCheckResponse {
        val response = client.get("/auth/token") {
            // TODO(sungbin): `DuckieHttpHeaders.Authorization` 로 변경
            // https://sungbinland.slack.com/archives/C046SS32SEQ/p1674118939394629?thread_ts=1674118269.577739&cid=C046SS32SEQ
            headers.append(/*DuckieHttpHeaders.Authorization*/ "authorization", "Bearer $token")
        }
        return responseCatching(
            response = response.body(),
            parse = AccessTokenCheckResponseData::toDomain,
        )
    }

    override fun attachAccessTokenToHeader(accessToken: String) {
        DuckieAuthorizationHeader.updateAccessToken(accessToken)
        client.update()

        val baseHeaders = FuelManager.instance.baseHeaders.orEmpty()
        FuelManager.instance.baseHeaders = baseHeaders.toMutableMap().apply {
            set(DuckieHttpHeaders.Authorization, "Bearer $accessToken")
        }
    }
}
