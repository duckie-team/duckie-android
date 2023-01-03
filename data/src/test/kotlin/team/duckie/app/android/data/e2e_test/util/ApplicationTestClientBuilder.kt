/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.e2e_test.util

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.ApplicationTestBuilder

fun ApplicationTestBuilder.createContentNegotiationClient(): HttpClient {
    return createClient {
        install(ContentNegotiation) {
            jackson()
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}
