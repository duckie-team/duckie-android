package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported

interface ScoreRepository : Repository {
    /**
     * [피드 선호도][FeedScore] 정보를 생성하거나 업데이트합니다.
     *
     * @param feedScore [피드 선호도][FeedScore] 정보
     *
     * @return 업데이트 성공 여부
     */
    suspend fun updateFeedScore(
        feedScore: FeedScore,
    ): Boolean

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
     * @return 업데이트 성공 여부
     */
    @Unsupported
    suspend fun updateContentStayTime(
        contentStayTime: ContentStayTime,
    ): Boolean
}
