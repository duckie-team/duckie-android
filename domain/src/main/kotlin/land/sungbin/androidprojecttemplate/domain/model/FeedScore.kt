package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.IntRange
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireRange

/**
 * 덕키 추천 시스템에 사용될 피드 점수 모델
 *
 * 각 필드별 업데이트된 값만 새로 받기 위해 모든 필드는
 * nullable 입니다. 단, 적어도 한 필드는 값이 있어야 합니다.
 *
 * @param userId [유저 아이디][User.nickname]
 * @param feedId [피드 아이디][Feed.id]
 * @param stayTime 해당 피드에 머문 시간. (단위: 초)
 * @param score 추천 시스템에 반영될 점수.
 *
 * | 상황 | 점수 |
 * | --- | :---: |
 * | 피드에 떴을 때 | 0 |
 * | 피드를 클릭 안하고 그냥 읽었을 때 | 1 |
 * | 피드를 클릭해서 자세히 읽었을 때 | 2 |
 * | 좋아요 눌렀을 때 | 3 |
 * | 좋아요를 취소했을 때 | -3 |
 * | 댓글 작성 | 5 |
 * | 댓글 삭제 | -5 |
 * | 공유 | 5 |
 * | 관심 없음 | -3 |
 */
data class FeedScore(
    @PK @FK val userId: String,
    @FK val feedId: String,
    @IntRange(from = 0) val stayTime: Int? = null,
    @IntRange(from = -5, to = 5) val score: Int? = null,
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
        if (stayTime == null && score == null) {
            throw IllegalArgumentException("stayTime and score cannot be null at the same time.")
        }
        if (score != null) {
            requireRange(
                field = "score",
                value = score,
                min = -5,
                max = 5,
            )
        }
    }
}
