package team.duckie.app.android.domain.usecase.fetch

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.Heart
import team.duckie.app.domain.model.constraint.HeartTarget
import team.duckie.app.domain.repository.FetchRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult
import team.duckie.app.domain.repository.result.getOrThrow
import team.duckie.app.domain.repository.result.runDuckApiCatching
import team.duckie.app.domain.usecase.fetch.cache.CacheType
import team.duckie.app.domain.usecase.fetch.cache.invokeOrLoadCache

class FetchAllFeedsUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 전체 [피드][Feed] 목록과 각각 [피드][Feed]에 대한 [좋아요][Heart] 정보를 조회합니다.
     * 추천 시스템이 반영된 피드 목록 조회는 [FetchRecommendationFeedsUseCase] 를 사용하세요.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * 자동으로 캐싱이 적용됩니다.
     *
     * @param force 캐시를 무시하고 요청할지 여부
     * @return 전체 [피드][Feed] 목록과 각각 [피드][Feed]에 대한 [좋아요][Heart] 정보를 담은 [fetch 결과][DuckFetchResult].
     * [Pair] 를 이용해 [피드][Feed] 목록과 [좋아요][Heart] 정보를 반환합니다.
     */
    suspend operator fun invoke(
        force: Boolean = false,
    ): DuckApiResult<List<Pair<Feed, List<Heart>>>> = invokeOrLoadCache(
        type = CacheType.AllFeeds,
        pk = Unit.toString(), // 피드는 특정 키로 한정지어 캐싱할 필요가 없음
        forceUpdate = force,
    ) {
        runDuckApiCatching {
            coroutineScope {
                val feeds = repository.fetchAllFeeds().getOrThrow()
                    ?: return@coroutineScope DuckFetchResult.Empty()
                val feedWithHearts = feeds.map { feed ->
                    async {
                        feed to (repository.fetchHearts(
                            target = HeartTarget.Feed,
                            targetId = feed.id,
                        ).getOrThrow() ?: emptyList())
                    }
                }
                DuckFetchResult.Success(
                    value = feedWithHearts.awaitAll(),
                )
            }
        }
    }
}
