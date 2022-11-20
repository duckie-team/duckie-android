package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.SaleRequest
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.repository.FetchRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult
import team.duckie.app.domain.repository.result.runDuckApiCatching
import team.duckie.app.domain.usecase.fetch.cache.CacheType
import team.duckie.app.domain.usecase.fetch.cache.invokeOrLoadCache

class FetchSaleRequestsUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 주어진 덕딜피드로부터 [판매 요청][SaleRequest]된 기록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * 자동으로 캐싱이 적용됩니다.
     *
     * @param feedId 조회할 [덕딜피드 아이디][Feed.id].
     * 주어진 피드 아이디가 덕딜피드 아이디인지는 검사하지 않습니다.
     * 해당 아이디에 등록된 리뷰가 없다면 피드 종류에 관계 없이 항상
     * [DuckFetchResult.Empty] 를 반환합니다.
     * @param force 캐시를 무시하고 요청할지 여부
     * @return 조회된 [판매 요청][SaleRequest] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @FK feedId: String,
        force: Boolean = false,
    ): DuckApiResult<List<SaleRequest>> = invokeOrLoadCache(
        type = CacheType.SaleRequest,
        pk = feedId,
        forceUpdate = force,
    ) {
        runDuckApiCatching {
            repository.fetchSaleRequest(
                feedId = feedId,
            )
        }
    }
}
