package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.FetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

@Unsupported
class FetchChatReadUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 주어진 [유저][User]가 마지막으로 [읽은 채팅][ChatRead]의 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 마지막으로 [읽은 채팅][ChatRead]의 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @PK @FK userId: String,
    ) = runDuckApiCatching {
        repository.fetchChatRead(
            userId = userId,
        )
    }
}
