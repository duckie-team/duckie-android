/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("ConstPropertyName")

package team.duckie.app.ktor.client.plugin.e2e_test

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import team.duckie.app.ktor.client.plugin.DuckieAuthorizationHeader
import team.duckie.app.ktor.client.plugin.DuckieAuthorizationHeaderOrNothingPlugin
import team.duckie.app.ktor.client.plugin.util.LoggingRequestHeadersPlugin

private const val DuckieAuthorizationHeaderKey = "authorization"
private const val DuckieAuthorizationHeaderValue = "TEST"
private const val DuckieAuthorizationHeaderLog = "-> $DuckieAuthorizationHeaderKey: Bearer $DuckieAuthorizationHeaderValue;"

// TODO(sungbin): `runApplication` 테스트로 변경
class DuckieAuthorizationHeaderOrNothingTest {
    private val headers = mutableListOf<String>()
    private lateinit var client: HttpClient

    @Before
    fun setup() {
        DuckieAuthorizationHeader.resetAccessToken()
        client = HttpClient(engineFactory = CIO) {
            install(DuckieAuthorizationHeaderOrNothingPlugin)
            install(LoggingRequestHeadersPlugin) {
                writer = { header -> headers.add(header) }
            }
        }
    }

    @Test
    fun without_duckie_authorization_header() = runTest {
        headers.clear()
        client.get("https://example.com")
        expectThat(headers).doesNotContain(DuckieAuthorizationHeaderLog)
    }

    @Test
    fun with_duckie_authorization_header() = runTest {
        headers.clear()
        DuckieAuthorizationHeader.updateAccessToken(DuckieAuthorizationHeaderValue)
        client = client.config {}
        client.get("https://example.com")
        expectThat(headers).contains(DuckieAuthorizationHeaderLog)
    }

    @After
    fun closeClient() {
        client.close()
    }
}
