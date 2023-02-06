/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._datasource

import android.os.Build
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.interceptors.LogRequestInterceptor
import com.github.kittinunf.fuel.core.interceptors.LogResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.BuildConfig
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.ktor.client.plugin.addDuckieAuthorizationHeaderIfNeeded

private object ContentTypeValues {
    const val ApplicationJson = "application/json"
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object FuelClient {
    private val MaxTimeoutMillis = 3.seconds
    private const val BaseUrl = "http://api-staging.goose-duckie.com:3000"

    private var DeviceName = Build.MODEL
    private const val ClientName = "android"

    private operator fun invoke(): Fuel {
        with(FuelManager.instance) {
            basePath = BaseUrl
            timeoutInMillisecond = MaxTimeoutMillis.toInt()
            timeoutReadInMillisecond = MaxTimeoutMillis.toInt()
            baseHeaders = mutableMapOf(
                DuckieHttpHeaders.Client to ClientName,
                DuckieHttpHeaders.DeviceName to DeviceName,
                DuckieHttpHeaders.Version to BuildConfig.APP_VERSION_NAME,
                Headers.CONTENT_TYPE to ContentTypeValues.ApplicationJson,
            ).apply {
                addDuckieAuthorizationHeaderIfNeeded(headerKey = DuckieHttpHeaders.Authorization)
            }
            addRequestInterceptor(LogRequestInterceptor)
            addResponseInterceptor(LogResponseInterceptor)
        }
        return Fuel
    }

    @Provides
    fun provide(): Fuel = this()
}

/** [Response] -> [String] */
internal fun Response.bodyAsText() = body().asString(headers[Headers.CONTENT_TYPE].lastOrNull())
