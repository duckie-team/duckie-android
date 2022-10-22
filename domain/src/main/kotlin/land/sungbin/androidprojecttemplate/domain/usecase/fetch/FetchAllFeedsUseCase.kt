package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.repository.DuckFetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class FetchAllFeedsUseCase(
    private val repository: DuckFetchRepository,
) {
    /**
     * 전체 [피드][Feed] 목록을 조회합니다.
     * 추천 시스템이 반영된 피드 목록 조회는 [FetchRecommendationFeedsUseCase] 를 사용하세요.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @return 전체 [피드][Feed] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke() = runDuckApiCatching {
        repository.fetchAllFeeds()
    }
}
