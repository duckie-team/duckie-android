package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.IntRange
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireRange

/**
 * 덕키 추천 시스템에 사용될 피드 점수 모델
 *
 * @param userId [유저 아이디][User.nickname]
 * @param feedId [피드 아이디][Feed.id]
 * @param stayTime 해당 피드에 머문 시간. (단위: 초)
 * 현재는 이 값을 얻을 방법이 아직 구현되지 않았으므로
 * 기본 값인 0 을 그대로 사용합니다.
 * @param score 추천 시스템에 반영될 점수.
 *
 * | 상황 | 점수 |
 * | --- | :---: |
 * | 피드에 떴을 때 | 0 |
 * | 피드를 클릭 안하고 그냥 읽었을 때 | 1 |
 * | 피드를 클릭해서 자세히 읽었을 때 | 2 |
 * | 좋아요 눌렀을 때 | 3 |
 * | 댓글 작성 | 5 |
 * | 공유 | 5 |
 * | 관심 없음 | -3 |
 */
@Unsupported
data class FeedScore(
    @PK @FK val userId: String,
    @FK val feedId: String,
    @IntRange(from = 0) val stayTime: Int = 0,
    val score: Int,
) {
    init {
        requireInput(
            field = "userId",
            value = userId,
        )
        requireInput(
            field = "feedId",
            value = feedId,
        )
        requireRange(
            min = 0,
            field = "stayTime",
            value = stayTime,
        )
    }
}
