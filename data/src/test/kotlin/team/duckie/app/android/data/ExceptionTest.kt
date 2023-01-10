/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package team.duckie.app.android.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectCatching
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import team.duckie.app.android.data._exception.model.ExceptionBody
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonMapper
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.data.util.buildMockHttpClient
import team.duckie.app.android.util.kotlin.DuckieResponseException

class ExceptionTest {
    private val exception = DuckieResponseException(
        code = "1000",
        message = "error",
        errors = listOf("awesome-error"),
        throwable = Throwable("ExceptionTest"),
    )
    private val exceptionContent = jsonMapper.writeValueAsString(
        ExceptionBody(
            code = exception.code,
            message = exception.originalMessage,
            errors = exception.errors,
        ),
    )
    private val client = buildMockHttpClient(content = exceptionContent)

    @Test
    fun response_catching_with_object() = runTest {
        val response = client.get("")

        expectCatching {
            responseCatching(
                response = response.body(),
                parse = UserResponse::toDomain,
            )
        }
            .isFailure()
            .run {
                with(subject as DuckieResponseException) {
                    get { code } isEqualTo exception.code
                    get { message } isEqualTo exception.message
                    get { errors } isEqualTo exception.errors
                }
            }
    }

    @Test
    fun response_catching_with_string() = runTest {
        val response = client.get("")

        expectCatching {
            responseCatching(response.bodyAsText()) { body ->
                body.toStringJsonMap()["hi"]
            }
        }
            .isFailure()
            .run {
                with(subject as DuckieResponseException) {
                    get { code } isEqualTo exception.code
                    get { message } isEqualTo exception.message
                    get { errors } isEqualTo exception.errors
                }
            }
    }
}
