/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin.exception

import kotlinx.collections.immutable.persistentListOf

object ExceptionCode {
    // 401
    const val TOKEN_EXPIRED = "TOKEN_EXPIRED"

    // 404
    const val USER_NOT_FOUND = "USER_NOT_FOUND"

    // 409
    const val TAG_ALREADY_EXISTS = "TAG_ALREADY_EXISTS"

    // 클라이언트 내부 code
    const val ClientMeIdNull = "client_me_id_null"
    const val ClientMeTokenNull = "client_me_token_null"
    const val ServerUserIdStrange = "server_user_id_strange"
}

val Throwable.isTagAlreadyExist: Boolean
    get() = (this as? DuckieResponseException)?.code == ExceptionCode.TAG_ALREADY_EXISTS

val Throwable.isUserNotFound: Boolean
    get() = (this as? DuckieResponseException)?.code == ExceptionCode.USER_NOT_FOUND

val Throwable.isTokenExpired: Boolean
    get() = (this as? DuckieResponseException)?.code == ExceptionCode.TOKEN_EXPIRED

/** 앱 내에서 로그인이 필요한 [코드값][DuckieException.code]인 경우인지 체크한다. */
val Throwable.isLoginRequireCode: Boolean
    get() = (this as? DuckieException)?.code in persistentListOf(
        ExceptionCode.ClientMeIdNull,
        ExceptionCode.ClientMeTokenNull,
        ExceptionCode.ServerUserIdStrange,
    )
