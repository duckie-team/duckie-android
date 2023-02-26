/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin.exception

object ExceptionCode {
    const val TAG_ALREADY_EXISTS = "TAG_ALREADY_EXISTS"
}

val Throwable.isTagAlreadyExist: Boolean
    get() = (this as? DuckieResponseException)?.code == ExceptionCode.TAG_ALREADY_EXISTS
