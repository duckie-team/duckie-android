package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.FetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.getOrThrow
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class FetchRecommendationFeedsUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 특정 유저의 취향이 반영된 [피드][Feed] 목록과 각각 [피드][Feed]에 대한 [좋아요][Heart] 정보를 조회합니다.
     * 내부적으로 [duckie-recommender-sysytem](https://github.com/duckie-team/duckie-recommender-system) 을 이용합니다.
     * 추천 시스템이 적용되지 않은 전체 [피드][Feed] 목록 조회는 [FetchAllFeedsUseCase] 를 사용하세요.
     *
     * 추천된 피드는 실시간 [피드 점수][FeedScore] 상태에 따라 바뀔 수 있으므로 값 캐싱을 하지 않습니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 추천된 [피드][Feed] 목록과 각각 [피드][Feed]에 대한 [좋아요][Heart] 정보를 담은 [fetch 결과][DuckFetchResult].
     * [Pair] 를 이용해 [피드][Feed] 목록과 [좋아요][Heart] 정보를 반환합니다.
     */
    suspend operator fun invoke(
        @PK userId: String,
    ) = runDuckApiCatching {
        coroutineScope<DuckApiResult<List<Pair<Feed, List<Heart>>>>> {
            val feeds = repository.fetchRecommendationFeeds(
                userId = userId,
            ).getOrThrow() ?: return@coroutineScope DuckFetchResult.Empty()
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
