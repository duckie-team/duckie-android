package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import androidx.annotation.IntRange
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

@Unsupported
class UpsertFeedScoreUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [피드 선호도][FeedScore] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param userId [유저 아이디][User.nickname]
     * @param feedId [피드 아이디][Feed.id]
     * @param stayTime 해당 피드에 머문 시간. (단위: 초)
     * 현재는 이 값을 얻을 방법이 아직 구현되지 않았으므로
     * 기본 값인 0 을 그대로 사용합니다.
     * @param score 추천 시스템에 반영될 점수
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @PK @FK userId: String,
        @FK feedId: String,
        @IntRange(from = 0) stayTime: Int = 0,
        score: Int,
    ) = runDuckApiCatching {
        repository.upsertFeedScore(
            feedScore = FeedScore(
                userId = userId,
                feedId = feedId,
                stayTime = stayTime,
                score = score,
            ),
        )
    }
}
