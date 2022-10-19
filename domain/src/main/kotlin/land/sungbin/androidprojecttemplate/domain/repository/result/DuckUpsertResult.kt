package land.sungbin.androidprojecttemplate.domain.repository.result

import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository

/**
 * [DuckUpsertRepository] 의 결과를 나타냅니다.
 */
sealed class DuckUpsertResult<T> : DuckApiResult<T> {
    /**
     * [DuckUpsertRepository] 에서 값 upsert 에 성공했을 때 반환되는 결과입니다.
     */
    class Done<T> : DuckUpsertResult<T>()
}
