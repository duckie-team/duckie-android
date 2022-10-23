package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.FetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class FetchUserUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 주어진 아이디로부터 [유저][User] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param id 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 [유저][User] 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @PK id: String,
    ) = runDuckApiCatching {
        repository.fetchUser(
            id = id,
        )
    }
}
