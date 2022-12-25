/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin

class DuckieApiException(
    override val message: String,
    val code: String? = null,
    val errors: List<String>? = null,
) : Exception(message)
