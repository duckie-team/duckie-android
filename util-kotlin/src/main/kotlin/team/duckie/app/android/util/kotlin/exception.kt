/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.app.android.util.kotlin

open class DuckieApiException(
    override val message: String,
    val code: String? = null,
    val errors: List<String>? = null,
) : Exception(message) {
    override fun toString(): String {
        return "DuckieApiException(message=$message, code=$code, errors=$errors)"
    }
}

class DuckieResponseNPE(requiredFieldName: String) : DuckieApiException(
    code = "400",
    message = "필수 필드가 response에 누락되었습니다. 누락된 필드명: $requiredFieldName",
)
