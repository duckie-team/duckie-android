package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.model.util.requireInput

/**
 * 좋아요 모델
 *
 * @param type 좋아요 이벤트를 받은 대상 타입
 * @param feedId 해당 타입에 맞는 아이디.
 * [피드 아이디][Feed.id] 혹은 [댓글 아이디][Comment.id]가 될 수 있습니다.
 * @param userId 해당 이벤트를 발생시킨 [유저 아이디][User.nickname]
 */
data class Heart(
    val type: HeartTarget,
    val feedId: String,
    val userId: String,
) {
    init {
        requireInput(
            field = "feedId",
            value = feedId,
        )
        requireInput(
            field = "userId",
            value = userId,
        )
    }
}

/** 좋아요 대상 */
enum class HeartTarget(
    val index: Int,
    val description: String,
) {
    Feed(
        index = 0,
        description = "피드",
    ),
    Comment(
        index = 1,
        description = "댓글",
    ),
}
