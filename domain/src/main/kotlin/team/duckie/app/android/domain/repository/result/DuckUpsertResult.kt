package team.duckie.app.android.domain.repository.result

import team.duckie.app.domain.repository.UpsertRepository

/**
 * [UpsertRepository] 의 결과를 나타냅니다.
 */
sealed class DuckUpsertResult<T> : DuckApiResult<T> {
    /**
     * [UpsertRepository] 에서 값 upsert 에 성공했을 때 반환되는 결과입니다.
     */
    class Done<T> : DuckUpsertResult<T>()
}

/**
 * [DuckApiResult] 가 [DuckUpsertResult.Done] 일 때 주어진
 * 콜백을 실행하는 확장 함수 입니다.
 *
 * @receiver [UpsertRepository] 로 부터 받은 결과
 * @param onDone [DuckUpsertResult.Done] 일 때 실행할 콜백
 * @return receiver 로 받는 [DuckApiResult].
 * method chaining 을 위해 반환합니다.
 */
fun DuckApiResult<*>.onDone(
    onDone: () -> Unit,
): DuckApiResult<*> = when (this) {
    is DuckUpsertResult.Done -> {
        onDone()
        this
    }
    else -> this
}
