package land.sungbin.androidprojecttemplate.domain.usecase.score

import androidx.annotation.IntRange
import land.sungbin.androidprojecttemplate.domain.constants.ScoreType
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.DuckScoreRepository

class UpdateFeedScoreUseCase(
    private val repository: DuckScoreRepository,
) {
    /**
     * [피드 선호도][FeedScore] 정보를 생성하거나 업데이트합니다.
     *
     * [stayTime] 과 [scoreType] 중에서 최소 하나는 제공해야 합니다.
     *
     * @param userId [유저 아이디][User.nickname]
     * @param feedId [피드 아이디][Feed.id]
     * @param stayTime 해당 피드에 머문 시간. (단위: 초)
     * 이 값은 선택이며, 만약 머문 시간이 아닌 단순 점수만 변경이라면 기본값인 null 을
     * 그대로 제공해야 합니다.
     * @param scoreType 추천 시스템에 반영될 점수.
     * 이 값은 선택이며, 만약 점수 변경이 아닌 단순 머문 시간만 변경이라면 기본값인
     * null 을 그대로 제공해야 합니다.
     *
     * @return 업데이트 성공 여부
     */
    suspend operator fun invoke(
        @PK @FK userId: String,
        @FK feedId: String,
        @IntRange(from = 0) stayTime: Int? = null,
        scoreType: ScoreType? = null,
    ) = runCatching {
        repository.updateFeedScore(
            feedScore = FeedScore(
                userId = userId,
                feedId = feedId,
                stayTime = stayTime,
                score = scoreType?.score,
            ),
        )
    }
}
