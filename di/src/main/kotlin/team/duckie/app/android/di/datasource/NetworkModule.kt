/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.jackson.jackson
import team.duckie.app.android.util.kotlin.seconds

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    // TODO(sungbin): header 에 access token 설정
    @Provides
    fun provideKtorClient(): HttpClient = HttpClient(engineFactory = CIO) {
        expectSuccess = true
        engine {
            endpoint {
                connectTimeout = MaxTimeoutMillis
                connectAttempts = MaxRetryCount
            }
        }
        defaultRequest {
            url(urlString = BaseUrl)
            headers.append(
                name = HttpHeaders.ContentType,
                value = ContentTypeJson,
            )
        }
        install(plugin = ContentNegotiation) {
            jackson()
        }
        install(plugin = Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }

    private companion object {
        val MaxTimeoutMillis = 3.seconds
        const val MaxRetryCount = 3
        const val BaseUrl = "http://api-staging.goose-duckie.com:3000"
        const val ContentTypeJson = "application/json"
    }
}
