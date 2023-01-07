/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._exception.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.util.kotlin.duckieResponseException
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe

internal data class ExceptionBody(
    @field:JsonProperty("code")
    val code: String? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("errors")
    val errors: List<String?>? = null,
)

internal fun ExceptionBody.throwing(throwable: Throwable): Nothing {
    duckieResponseException(
        message = message,
        code = code ?: duckieResponseFieldNpe("code"),
        errors = errors?.filterNotNull(),
        throwable = throwable,
    )
}
