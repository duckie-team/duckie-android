package team.duckie.app.android.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.jackson.jackson
import team.duckie.app.android.data.Constants.BaseUrl
import team.duckie.app.android.data.Constants.MaxRetryCount
import team.duckie.app.android.data.Constants.MaxTimeoutMillis
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @Provides
    fun provideBaseUrl() = BaseUrl//TODO BuildConfig.URL

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(CIO) {
        expectSuccess = true
        engine {
            endpoint {
                connectTimeout = MaxTimeoutMillis
                connectAttempts = MaxRetryCount
            }
        }
        install(plugin = ContentNegotiation) {
            jackson()
        }
        install(plugin = Logging) {
            logger = Logger.ANDROID
            level = LogLevel.BODY
        }
    }
}
