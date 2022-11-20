package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.Heart
import team.duckie.app.domain.model.constraint.HeartTarget
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.model.util.Unsupported
import team.duckie.app.domain.repository.FetchRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult
import team.duckie.app.domain.repository.result.runDuckApiCatching
import team.duckie.app.domain.usecase.fetch.cache.CacheType
import team.duckie.app.domain.usecase.fetch.cache.invokeOrLoadCache

@Unsupported
class FetchHeartsUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 주어진 피드가 받은 [좋아요][Heart] 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * 자동으로 캐싱이 적용됩니다.
     *
     * @param target 조회할 [좋아요][Heart]의 대상
     * @param feedId 조회할 [피드 아이디][Feed.id]
     * @param force 캐시를 무시하고 요청할지 여부
     *
     * @return 조회된 [좋아요][Heart] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        target: HeartTarget,
        @PK @FK feedId: String,
        force: Boolean = false,
    ): DuckApiResult<List<Heart>> = invokeOrLoadCache(
        type = CacheType.Hearts,
        pk = feedId,
        forceUpdate = force,
    ) {
        runDuckApiCatching {
            repository.fetchHearts(
                target = target,
                targetId = feedId,
            )
        }
    }
}
