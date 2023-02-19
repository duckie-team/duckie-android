/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.ktor.client.plugin

import io.ktor.client.plugins.api.createClientPlugin

object DuckieAuthorizationHeader {
    internal var accessToken: String? = null

    fun updateAccessToken(token: String) {
        accessToken = token
    }

    fun resetAccessToken() {
        accessToken = null
    }
}

val DuckieAuthorizationHeaderOrNothingPlugin = createClientPlugin(
    name = "DuckieAuthorizationHeaderOrNothing",
    createConfiguration = ::DuckieAuthorizationHeaderConfig,
) {
    DuckieAuthorizationHeader.accessToken?.let { accessToken ->
        val headerKey = pluginConfig.headerKey
        onRequest { request, _ ->
            if (headerKey !in request.headers) {
                request.headers.append(headerKey, "Bearer $accessToken")
            }
        }
    }
}

fun MutableMap<String, String>.addDuckieAuthorizationHeaderIfNeeded(headerKey: String) = apply {
    DuckieAuthorizationHeader.accessToken?.let { accessToken ->
        set(headerKey, "Bearer $accessToken")
    }
}

class DuckieAuthorizationHeaderConfig {
    var headerKey: String = "authorization"
}
