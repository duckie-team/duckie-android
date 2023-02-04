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
            request.headers.append(headerKey, "Bearer $accessToken")
        }
    }
}

// TODO(riflockle7): 의존 관계가 꼬여있는듯한 느낌이 있음. Api Header 명세 내용을 해당 모듈로 옮길 필요가 있음
//   일단은 하드코딩으로 처리
fun MutableMap<String, String>.addDuckieAuthorizationHeader() = this.apply {
    DuckieAuthorizationHeader.accessToken?.let { put("Authorization", "Bearer $it") }
}

class DuckieAuthorizationHeaderConfig {
    var headerKey: String = "authorization"
}
