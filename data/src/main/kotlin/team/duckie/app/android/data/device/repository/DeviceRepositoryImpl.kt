/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.device.repository

import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.domain.device.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor() : DeviceRepository {
    override suspend fun register(fcmToken: String) {
        val response = client.post("/devices") {
            jsonBody {
                "token" withString fcmToken
            }
        }
        response.bodyAsText()
    }

    override suspend fun unRegister(userId: Int): Boolean {
        val response = client.delete("/devices/$userId")
        return responseCatching(response.status.value, response.bodyAsText()) { responseBody ->
            val json = responseBody.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }
}
