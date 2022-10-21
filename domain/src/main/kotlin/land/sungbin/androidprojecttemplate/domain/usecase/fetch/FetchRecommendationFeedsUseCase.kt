package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.DuckFetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class FetchRecommendationFeedsUseCase(
    private val repository: DuckFetchRepository,
) {
    /**
     * 특정 유저의 취향이 반영된 [피드][Feed] 목록을 조회합니다.
     * 내부적으로 [duckie-recommender-sysytem](https://github.com/duckie-team/duckie-recommender-system) 을 이용합니다.
     * 추천 시스템이 적용되지 않은 전체 [피드][Feed] 목록 조회는 [FetchAllFeedsUseCase] 를 사용하세요.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 추천된 [피드][Feed] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @PK userId: String,
    ) = runDuckApiCatching {
        repository.fetchRecommendationFeeds(
            userId = userId,
        )
    }
}
