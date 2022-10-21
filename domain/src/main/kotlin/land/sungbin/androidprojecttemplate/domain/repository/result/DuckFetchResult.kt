package land.sungbin.androidprojecttemplate.domain.repository.result

import land.sungbin.androidprojecttemplate.domain.repository.DuckFetchRepository

/**
 * [DuckFetchRepository] 의 결과를 나타냅니다.
 */
sealed class DuckFetchResult<T> : DuckApiResult<T> {
    /**
     * [DuckFetchRepository] 에서 값 fetch 에 성공했을 때 반환되는 결과입니다.
     *
     * @param value fetch 된 값
     */
    data class Success<T>(val value: T) : DuckFetchResult<T>()

    /**
     * [DuckFetchRepository] 에서 fetch 된 값이 없을 때 반환되는 결과입니다.
     */
    class Empty<T> : DuckFetchResult<T>()
}

/**
 * [DuckApiResult] 가 [DuckFetchResult.Success] 일 때 주어진
 * 콜백을 실행하는 확장 함수 입니다.
 *
 * @receiver [DuckFetchRepository] 로 부터 받은 결과
 * @param onSuccess [DuckFetchResult.Success] 일 때 실행할 콜백
 * @return receiver 로 받는 [DuckApiResult].
 * method chaining 을 위해 반환합니다.
 */
fun <T> DuckApiResult<T>.onSuccess(
    onSuccess: (value: T) -> Unit,
): DuckApiResult<T> = when (this) {
    is DuckFetchResult.Success -> {
        onSuccess(
            /* value = */
            value,
        )
        this
    }
    else -> this
}

/**
 * [DuckApiResult] 가 [DuckFetchResult.Empty] 일 때 주어진
 * 콜백을 실행하는 확장 함수 입니다.
 *
 * @receiver [DuckFetchRepository] 로 부터 받은 결과
 * @param onEmpty [DuckFetchResult.Empty] 일 때 실행할 콜백
 * @return receiver 로 받는 [DuckApiResult].
 * method chaining 을 위해 반환합니다.
 */
fun DuckApiResult<*>.onEmpty(
    onEmpty: () -> Unit,
): DuckApiResult<*> = when (this) {
    is DuckFetchResult.Empty -> {
        onEmpty()
        this
    }
    else -> this
}
