/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._exception.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.util.kotlin.exception.DuckieStatusCode
import team.duckie.app.android.util.kotlin.exception.duckieResponseException
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

internal data class ExceptionBody(
    @JsonIgnore
    val statusCode: DuckieStatusCode = DuckieStatusCode.Unknown,

    @field:JsonProperty("code")
    val code: String? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("errors")
    val errors: List<String?>? = null,
)

internal fun ExceptionBody.throwing(): Nothing {
    duckieResponseException(
        statusCode = statusCode,
        serverMessage = message,
        code = code ?: duckieResponseFieldNpe("code"),
        errors = errors?.filterNotNull(),
    )
}
