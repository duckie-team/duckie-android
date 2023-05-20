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

package team.duckie.app.android.common.kotlin.exception

/**
 * 덕키 API나, 클라이언트 에러에 의존하지 않는, ThirdParty Library에 대한
 * 에러를 표현하고 싶을 때 사용합니다.
 *
 * @param message 예외 메시지. 선택으로 값을 받습니다.
 * @param code 예외 코드. 필수로 값을 받습니다.
 * @param errors 발생한 에러 목록. 선택으로 값을 받습니다
 */
class DuckieThirdPartyException(
    message: String? = null,
    override val code: String,
    val errors: List<String>? = null,
) : DuckieException(code) {
    override val message = message ?: ""

    override fun toString(): String {
        return "DuckieThirdPartyException(message=$message, code=$code, errors=$errors)"
    }
}

fun duckieThirdPartyException(
    message: String? = null,
    code: String,
    errors: List<String>? = null,
): Nothing {
    throw DuckieClientLogicProblemException(
        serverMessage = message,
        code = code,
        errors = errors,
    )
}
