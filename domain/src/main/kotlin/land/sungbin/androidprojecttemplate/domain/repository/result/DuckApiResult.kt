package land.sungbin.androidprojecttemplate.domain.repository.result

import land.sungbin.androidprojecttemplate.domain.repository.DuckRepository

/**
 * [DuckRepository] 의 결과를 나타냅니다.
 */
// TODO: 공변성 체크. 왜 `<out T>` 을 해야 하는지 아직 정확히 모름.
sealed interface DuckApiResult<T> {
    /**
     * [DuckRepository] 에서 API 호출에 예외가 발생했을 때 반환되는 결과입니다.
     *
     * @param message 발생한 예외 메시지
     */
    data class Exception<T>(val message: String?) : DuckApiResult<T>
}
