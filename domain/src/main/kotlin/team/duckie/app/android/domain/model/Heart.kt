package team.duckie.app.android.domain.model

import team.duckie.app.domain.model.constraint.HeartTarget
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.model.util.NewField
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.model.util.requireInput

/**
 * 좋아요 모델
 *
 * @param target 좋아요 이벤트를 받은 대상 타입
 * @param targetId 좋아요 이벤트를 받은 대상 아이디.
 * [피드 아이디][Feed.id] 혹은 [댓글 아이디][Comment.id]가 될 수 있습니다.
 * @param userId 해당 이벤트를 발생시킨 [유저 아이디][User.nickname]
 */
data class Heart(
    @NewField val target: HeartTarget,
    @PK @FK val targetId: String,
    @PK @FK val userId: String,
) {
    init {
        requireInput(
            field = "targetId",
            value = targetId,
        )
        requireInput(
            field = "userId",
            value = userId,
        )
    }
}

