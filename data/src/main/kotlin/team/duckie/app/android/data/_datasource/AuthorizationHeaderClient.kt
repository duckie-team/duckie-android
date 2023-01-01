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
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.serialization.jackson.jackson
import team.duckie.app.android.data.BuildConfig
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.ktor.client.plugin.DuckieAuthorizationHeaderOrNothingPlugin

internal var client = AuthorizationHeaderClient()
    private set

internal fun updateClient(newClient: HttpClient) {
    client = newClient
}

internal object AuthorizationHeaderClient {
    private val MaxTimeoutMillis = 3.seconds
    private const val MaxRetryCount = 3
    private const val BaseUrl = "http://api-staging.goose-duckie.com:3000"

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
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append("x-duckie-device-name", Build.MODEL)
                append("x-duckie-version", BuildConfig.APP_VERSION_NAME)
                append("x-duckie-client", "android")
            }
        }
        install(plugin = DuckieAuthorizationHeaderOrNothingPlugin)
        install(plugin = ContentNegotiation) {
            jackson()
        }
        install(plugin = Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}
