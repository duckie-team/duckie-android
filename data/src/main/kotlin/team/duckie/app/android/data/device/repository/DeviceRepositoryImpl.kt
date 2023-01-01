/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.device.repository

import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.domain.device.repository.DeviceRepository

class DeviceRepositoryImpl @Inject constructor() : DeviceRepository {
    override suspend fun register(fcmToken: String) {
        val response = client.post("/devices") {
            jsonBody {
                "token" withString fcmToken
            }
        }
        response.bodyAsText()
    }
}
