/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.app.android.util.kotlin.exception

import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.runIf

/**
 * 덕키 API 에서 발생하는 [Exception] 을 정의합니다.
 *
 * 백엔드의 [에러 처리 규칙](https://www.notion.so/jisungbin/6487a9d604e14375bc02df6fd8397f15)에 따라 [code] 를 필수로 받습니다.
 *
 * @param code 예외 코드
 */
sealed class DuckieException(open val code: String) :
    IllegalStateException("DuckieException: $code")

/** API 에러로 인지되는 임계점. 아래 선언 값은 에러로 포함된다. */
const val ApiErrorThreshold = 400
private const val UnknownCode = -1
private const val BadRequestCode = 400
private const val UnAuthorizedCode = 401
private const val ForbiddenCode = 403
private const val NotFoundCode = 404
private const val ConflictCode = 409
private const val InternalServerCode = 500

/** 서버 응답 StatusCode */
@AllowMagicNumber("Server statusCode 구분 값 MagicNumber 허용해야 함")
enum class DuckieStatusCode(val statusCode: Int) {
    Unknown(UnknownCode),
    BadRequest(BadRequestCode),
    UnAuthorized(UnAuthorizedCode),
    Forbidden(ForbiddenCode),
    NotFound(NotFoundCode),
    Conflict(ConflictCode),
    InternalServer(InternalServerCode),
}

/** API 오류인지 체크한다. */
@Suppress("unused")
val DuckieStatusCode.isApiError: Boolean
    get() = this.statusCode >= ApiErrorThreshold

/** [Int] -> [DuckieStatusCode] */
fun Int.toDuckieStatusCode(): DuckieStatusCode = when (this) {
    BadRequestCode -> DuckieStatusCode.BadRequest
    UnAuthorizedCode -> DuckieStatusCode.UnAuthorized
    ForbiddenCode -> DuckieStatusCode.Forbidden
    NotFoundCode -> DuckieStatusCode.NotFound
    ConflictCode -> DuckieStatusCode.Conflict
    InternalServerCode -> DuckieStatusCode.InternalServer
    else -> DuckieStatusCode.Unknown
}

/**
 * 덕키 API 의 response 가 정상적으로 처리되지 않았을 때 [DuckieException] 을 나타냅니다.
 *
 * 백엔드의 [에러 처리 규칙](https://www.notion.so/jisungbin/6487a9d604e14375bc02df6fd8397f15) 을 따릅니다.
 *
 * @param serverMessage 예외 메시지. 선택으로 값을 받습니다.
 * @param code 예외 코드. 필수로 값을 받습니다.
 * @param errors 발생한 에러 목록. 선택으로 값을 받습니다
 */
class DuckieResponseException(
    serverMessage: String? = null,
    val statusCode: DuckieStatusCode,
    override val code: String,
    val errors: List<String>? = null,
) : DuckieException(code) {
    override val message =
        "DuckieResponseException: $code".runIf(serverMessage != null) { plus(" - $serverMessage") }

    override fun toString(): String {
        return "DuckieApiException(message=$message, statusCode=$statusCode, code=$code, errors=$errors)"
    }
}

fun duckieResponseException(
    statusCode: DuckieStatusCode,
    serverMessage: String? = null,
    code: String,
    errors: List<String>? = null,
): Nothing {
    throw DuckieResponseException(
        statusCode = statusCode,
        serverMessage = serverMessage,
        code = code,
        errors = errors,
    )
}

/**
 * 덕키 API 의 response 에서 필수 필드가 null 로 나왔을 때 [DuckieException] 을 나타냅니다.
 *
 * @param field 누락된 필수 필드명
 */
class DuckieResponseFieldNPE(field: String) : DuckieException(code = "") {
    override val message = "필수 필드가 response 에 누락되었습니다. 누락된 필드명: $field"
}

fun duckieResponseFieldNpe(field: String): Nothing {
    throw DuckieResponseFieldNPE(field = field)
}

/**
 * 클라이언트 내부 오류를 표현하고 싶을 때 나타냅니다.
 *
 * @param serverMessage 예외 메시지. 선택으로 값을 받습니다.
 * @param code 예외 코드. 필수로 값을 받습니다.
 * @param errors 발생한 에러 목록. 선택으로 값을 받습니다
 */
class DuckieClientLogicProblemException(
    serverMessage: String? = null,
    override val code: String,
    val errors: List<String>? = null,
) : DuckieException(code) {
    override val message =
        "DuckieResponseException: $code".runIf(serverMessage != null) { plus(" - $serverMessage") }

    override fun toString(): String {
        return "DuckieApiException(message=$message, code=$code, errors=$errors)"
    }
}

fun duckieClientLogicProblemException(
    message: String? = null,
    code: String = "client_logic_problem",
    errors: List<String>? = null,
): Nothing {
    throw DuckieClientLogicProblemException(
        serverMessage = message,
        code = code,
        errors = errors,
    )
}

/** 앱 내에서 로그인이 필요한 [코드값][DuckieException.code]인 경우인지 체크한다. */
fun DuckieException.isLoginRequireCode() =
    this.code in persistentListOf(ClientMeIdNull, ClientMeTokenNull, ServerUserIdStrange)

// TODO(riflockle7): exception 관련 모듈로 옮김 고려 필요
const val ClientMeIdNull = "client_me_id_null"
const val ClientMeTokenNull = "client_me_token_null"

const val ServerUserIdStrange = "server_user_id_strange"
