package land.sungbin.androidprojecttemplate.domain.repository.result

import land.sungbin.androidprojecttemplate.domain.repository.DuckRepository

/**
 * [DuckRepository] 의 결과를 나타냅니다.
 */
sealed interface DuckApiResult<T> {
    /**
     * [DuckRepository] 에서 API 호출에 예외가 발생했을 때 반환되는 결과입니다.
     *
     * @param message 발생한 예외 메시지
     */
    data class Exception<T>(val message: String?) : DuckApiResult<T>
}

/**
 * [runCatching] 과 유사하며, [DuckRepository] 작업 중 예외 발생시 [DuckApiResult.Exception] 을
 * 반환합니다.
 *
 * @param execute 실행할 [DuckRepository] 작업
 * @return [execute] 작업의 결과.
 * 만약 [execute] 를 실행한 결과가 성공적이라면 그대로 [DuckApiResult] 를 반환하고,
 * 실행 도중에 예외가 발생했다면 예외 메시지를 [DuckApiResult.Exception] 으로 반환합니다.
 */
internal inline fun <T> runDuckApiCatching(
    execute: () -> DuckApiResult<T>,
) = try {
    execute()
} catch (exception: Throwable) {
    DuckApiResult.Exception(
        message = exception.message,
    )
}

/**
 * [DuckApiResult] 가 [DuckApiResult.Exception] 일 때 주어진
 * 콜백을 실행하는 확장 함수 입니다.
 *
 * @receiver [DuckRepository] 로 부터 받은 결과
 * @param onException [DuckApiResult.Exception] 일 때 실행할 콜백
 * @return receiver 로 받는 [DuckApiResult].
 * method chaining 을 위해 반환합니다.
 */
fun DuckApiResult<*>.onExcepion(
    onException: (message: String?) -> Unit,
): DuckApiResult<*> = when (this) {
    is DuckApiResult.Exception -> {
        onException(
            /* message = */
            message,
        )
        this
    }
    else -> this
}
