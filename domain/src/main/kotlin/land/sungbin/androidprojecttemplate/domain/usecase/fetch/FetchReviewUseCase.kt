package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.DuckFetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class FetchReviewUseCase(
    private val repository: DuckFetchRepository,
) {
    /**
     * 주어진 덕딜피드로부터 등록된 [리뷰][DealReview] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param feedId 조회할 [덕딜피드 아이디][Feed.id].
     * 주어진 피드 아이디가 덕딜피드 아이디인지는 검사하지 않습니다.
     * 해당 아이디에 등록된 리뷰가 없다면 피드 종류에 관계 없이 항상
     * [DuckFetchResult.Empty] 를 반환합니다.
     * @return 조회된 [리뷰][DealReview] 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @FK feedId: String,
    ) = runDuckApiCatching {
        repository.fetchReview(
            feedId = feedId,
        )
    }
}
