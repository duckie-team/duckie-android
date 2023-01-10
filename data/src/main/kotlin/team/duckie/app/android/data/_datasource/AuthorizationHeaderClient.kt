/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._datasource

import android.os.Build
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import team.duckie.app.android.data.BuildConfig
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.ktor.client.plugin.DuckieAuthorizationHeaderOrNothingPlugin

internal var client = AuthorizationHeaderClient()
    private set

// [DuckieHttpHeaders.DeviceName] 을 대체한다.
// mock 인 경우엔 오류가 발생하여 해당 변수에서 설정이 필요하다.
private var DeviceName = Build.MODEL

internal fun updateClient(
    newClient: HttpClient? = null,
    newBuildModel: String? = null,
) {
    newClient?.let { client = it }
    newBuildModel?.let { DeviceName = it }
}

internal object DuckieHttpHeaders {
    const val DeviceName = "x-duckie-device-name"
    const val Version = "x-duckie-version"
    const val Client = "x-duckie-client"
    // Authorization 가 AuthRepositoryImpl 에서 접근 필요
    const val Authorization = "X-DUCKIE-AUTHORIZATION"
}

private object AuthorizationHeaderClient {
    private val MaxTimeoutMillis = 3.seconds
    private const val MaxRetryCount = 3
    private const val BaseUrl = "http://api-staging.goose-duckie.com:3000"
    private const val ClientName = "android"

    operator fun invoke() = HttpClient(engineFactory = CIO) {
        expectSuccess = true
        engine {
            endpoint {
                connectTimeout = MaxTimeoutMillis
                connectAttempts = MaxRetryCount
            }
        }
        defaultRequest {
            url(BaseUrl)
            contentType(ContentType.Application.Json)
            headers {
                append(DuckieHttpHeaders.DeviceName, DeviceName)
                append(DuckieHttpHeaders.Version, BuildConfig.APP_VERSION_NAME)
                append(DuckieHttpHeaders.Client, ClientName)
            }
        }
        install(plugin = DuckieAuthorizationHeaderOrNothingPlugin) {
            headerKey = DuckieHttpHeaders.Authorization
        }
        install(plugin = ContentNegotiation) {
            jackson()
        }
        install(plugin = Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}
