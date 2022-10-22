package land.sungbin.androidprojecttemplate.domain.usecase.assert

import land.sungbin.androidprojecttemplate.domain.repository.DuckAssertRepository

class CheckNicknameAvailableUseCase(
    private val repository: DuckAssertRepository,
) {
    /**
     * 주어진 닉네임이 사용 가능한 상태인지 조회합니다.
     *
     * 중복되는 닉네임이 아니라면 사용 상태를 나타내고,
     * 이미 중복된다면 사용 불가능함을 나타냅니다.
     *
     * @param nickname 조회할 닉네임
     * @return 사용 가능 여부
     */
    suspend operator fun invoke(
        nickname: String,
    ) = runCatching {
        repository.checkNicknameAvailable(
            nickname = nickname,
        )
    }
}
