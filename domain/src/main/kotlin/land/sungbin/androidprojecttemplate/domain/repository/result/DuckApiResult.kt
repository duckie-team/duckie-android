package land.sungbin.androidprojecttemplate.domain.repository.result

/**
 * DuckRepository 들의 결과 모음
 */
// TODO: 공변성 체크
sealed class DuckApiResult<T> {
    /**
     *
     */
    data class Success<T>(val value: T) : DuckApiResult<T>()

    object Done : DuckApiResult<Nothing>()

    data class Exception(val message: String) : DuckApiResult<Nothing>()

    object Empty : DuckApiResult<Nothing>()
}