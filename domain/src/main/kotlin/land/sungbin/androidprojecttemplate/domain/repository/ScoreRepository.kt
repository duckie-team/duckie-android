package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

/**
 * 추천 시스템에 사용되는 모델들을 다루는 API 들의 시그니처를 정의합니다.
 */
interface ScoreRepository : DuckRepository {
    /**
     * [피드 선호도][FeedScore] 정보를 생성하거나 업데이트합니다.
     *
     * @param feedScore [피드 선호도][FeedScore] 정보
     *
     * @return 업데이트 성공 여부.
     * 성공 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun updateFeedScore(
        feedScore: FeedScore,
    ): DuckApiResult<Nothing>

    /**
     * [컨텐츠 별 머문 시간][ContentStayTime] 정보를 생성하거나 업데이트합니다.
     *
     * 모든 시간의 기준은 초를 사용합니다.
     * 또한 모든 시간 값의 필드는 선택적이며, 만약 해당 필드에 변경 사항이
     * 없다면 기본값인 null 을 그대로 제공해야 합니다.
     * 단, 모든 필드들 중에 최소 하나는 제공되야 합니다.
     *
     * @param contentStayTime [컨텐츠 별 머문 시간][ContentStayTime] 정보
     *
     * @return 업데이트 성공 여부.
     * 성공 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    @Unsupported
    suspend fun updateContentStayTime(
        contentStayTime: ContentStayTime,
    ): DuckApiResult<Nothing>
}
