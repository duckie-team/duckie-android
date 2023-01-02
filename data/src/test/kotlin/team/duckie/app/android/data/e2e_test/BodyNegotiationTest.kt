/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.e2e_test

import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.server.testing.testApplication
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data.e2e_test.module.bodyResponseModule
import team.duckie.app.android.data.e2e_test.util.createContentNegotiationClient
import team.duckie.app.android.domain.tag.model.Tag

class BodyNegotiationTest {
    @Test
    fun equal_plain_text_as_same_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            setBody("Hello, world!")
        }
        val actual = response.bodyAsText()

        val expected = "Hello, world!"

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun not_equal_plain_text_as_same_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            setBody("Bye, world!")
        }
        val actual = response.bodyAsText()

        val expected = "Hello, world!"

        expectThat(actual).isNotEqualTo(expected)
    }

    @Test
    fun equal_object_as_json_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            setBody(
                mapOf(
                    "name" to "Duckie",
                    "age" to 1,
                )
            )
        }
        val actual = response.bodyAsText()

        val expected = "{\"name\":\"Duckie\",\"age\":1}"

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun not_equal_object_as_json_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            setBody(
                mapOf(
                    "name" to "Duckie",
                    "age" to 100,
                )
            )
        }
        val actual = response.bodyAsText()

        val expected = "{\"name\":\"Duckie\",\"age\":1}"

        expectThat(actual).isNotEqualTo(expected)
    }

    @Test
    fun equal_pojo_as_json_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            setBody(Tag(id = 1, name = "Tag"))
        }
        val actual = response.bodyAsText()

        val expected = "{\"id\":1,\"name\":\"Tag\"}"

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun not_equal_pojo_as_json_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            setBody(Tag(id = 100, name = "Tag"))
        }
        val actual = response.bodyAsText()

        val expected = "{\"id\":1,\"name\":\"Tag\"}"

        expectThat(actual).isNotEqualTo(expected)
    }

    @Test
    fun equal_plain_json_as_same_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            jsonBody {
                "name" withString "Duckie"
                "age" withInt 1
            }
        }
        val actual = response.bodyAsText()

        val expected = "{\"name\":\"Duckie\",\"age\":1}"

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun not_equal_plain_json_as_same_body() = testApplication {
        val client = createContentNegotiationClient()
        application { bodyResponseModule() }

        val response = client.get("/") {
            jsonBody {
                "name" withString "Duckie"
                "age" withInt 100
            }
        }
        val actual = response.bodyAsText()

        val expected = "{\"name\":\"Duckie\",\"age\":1}"

        expectThat(actual).isNotEqualTo(expected)
    }
}
