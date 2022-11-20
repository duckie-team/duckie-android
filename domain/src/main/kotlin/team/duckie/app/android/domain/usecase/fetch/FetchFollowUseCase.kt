package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.model.Follow
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.repository.FetchRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult
import team.duckie.app.domain.repository.result.runDuckApiCatching
import team.duckie.app.domain.usecase.fetch.cache.CacheType
import team.duckie.app.domain.usecase.fetch.cache.invokeOrLoadCache


class FetchFollowUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 주어진 아이디로부터 [팔로우][Follow] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * 자동으로 캐싱이 적용됩니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @param force 캐시를 무시하고 요청할지 여부
     * @return 조회된 [팔로우][Follow] 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @PK userId: String,
        force: Boolean = false,
    ): DuckApiResult<Follow> = invokeOrLoadCache(
        type = CacheType.Follow,
        pk = userId,
        forceUpdate = force,
    ) {
        runDuckApiCatching {
            repository.fetchFollow(
                userId = userId,
            )
        }
    }
}
