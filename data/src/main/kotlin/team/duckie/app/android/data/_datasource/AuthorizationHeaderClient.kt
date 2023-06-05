/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("PrivatePropertyName")

package team.duckie.app.android.data._datasource

import android.os.Build
import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import team.duckie.app.android.common.kotlin.seconds
import team.duckie.app.android.data.BuildConfig
import team.duckie.app.ktor.client.plugin.DuckieAuthorizationHeaderOrNothingPlugin
import timber.log.Timber

/** token 이 필요한 HttpClient */
internal var client = AuthorizationHeaderClient()
    private set

/** token 이 필요 없는 HttpClient */
@Suppress("unused")
internal var noAuthClient = AuthorizationHeaderClient(false)
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

// TODO(sungbin): AuthorizationClient.kt 로 이전
internal object DuckieHttpHeaders {
    const val DeviceName = "x-duckie-device-name"
    const val Version = "x-duckie-version"
    const val Client = "x-duckie-client"

    // Authorization 가 AuthRepositoryImpl 에서 접근 필요
    const val Authorization = "Authorization"
}

private object AuthorizationHeaderClient {
    private val MaxTimeoutMillis = 3.seconds
    private const val MaxRetryCount = 3
    private const val BaseUrl = "https://api.goose-duckie.com:3000"
    private const val ClientName = "android"

    operator fun invoke(authorizationCheck: Boolean = true) = HttpClient(engineFactory = CIO) {
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
                append(DuckieHttpHeaders.Version, BuildConfig.APP_VERSION_NAME_NUMBER)
                append(DuckieHttpHeaders.Client, ClientName)
            }
        }
        if (authorizationCheck) {
            install(plugin = DuckieAuthorizationHeaderOrNothingPlugin) {
                headerKey = DuckieHttpHeaders.Authorization
            }
        }
        install(plugin = ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            }
        }
        install(plugin = Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                   Timber.e(message)
                }
            }
            level = LogLevel.ALL
        }
    }
}
