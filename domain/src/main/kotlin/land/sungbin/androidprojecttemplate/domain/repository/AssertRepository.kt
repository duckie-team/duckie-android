package land.sungbin.androidprojecttemplate.domain.repository

/**
 * 값 체크(assertion) 요청을 하는 API 들의 시그니처를 정의합니다.
 */
interface AssertRepository : DuckRepository {
    /**
     * 주어진 닉네임이 사용 가능한 상태인지 조회합니다.
     *
     * 중복되는 닉네임이 아니라면 사용 상태를 나타내고,
     * 이미 중복된다면 사용 불가능함을 나타냅니다.
     *
     * @param nickname 조회할 닉네임
     * @return 사용 가능 여부
     */
    suspend fun checkNicknameAvailable(
        nickname: String,
    ): Boolean
}
