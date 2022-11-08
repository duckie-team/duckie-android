package land.sungbin.androidprojecttemplate.data.di

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
import javax.inject.Singleton

@Suppress("KDocFields")
@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @Provides
    fun provideBaseUrl() = "https://cors-test.appspot.com/test" //TODO BuildConfig.URL

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(CIO){
        expectSuccess = true
        engine {
            endpoint {
                connectTimeout = 300
                connectAttempts = 3
            }
        }
        install(plugin = ContentNegotiation){
            jackson()
        }
        install(plugin = Logging){
            logger = Logger.ANDROID
            level = LogLevel.BODY
        }
    }
}