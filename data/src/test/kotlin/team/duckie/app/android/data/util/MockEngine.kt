/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("TestFunctionName")

package team.duckie.app.android.data.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.jackson.jackson

private fun MockEngine(status: HttpStatusCode, content: String): MockEngine {
    return MockEngine {
        respond(
            content = content,
            status = status,
            headers = headersOf(
                name = HttpHeaders.ContentType,
                value = ContentType.Application.Json.toString(),
            ),
        )
    }
}

fun buildMockHttpClient(
    status: HttpStatusCode = HttpStatusCode.OK,
    content: String,
): HttpClient {
    return HttpClient(
        engine = MockEngine(
            status = status,
            content = content,
        ),
    ) {
        expectSuccess = true
        install(plugin = ContentNegotiation) {
            jackson()
        }
    }
}
