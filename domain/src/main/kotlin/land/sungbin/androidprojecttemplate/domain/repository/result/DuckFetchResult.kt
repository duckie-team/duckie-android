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
