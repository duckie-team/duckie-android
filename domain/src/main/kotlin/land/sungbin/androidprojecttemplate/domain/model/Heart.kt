package land.sungbin.androidprojecttemplate.domain.model

/**
 * 좋아요 모델
 *
 * @param type 좋아요 이벤트를 받은 대상 타입
 * @param feedId 해당 타입에 맞는 아이디.
 * [피드 아이디][Feed.id] 혹은 [댓글 아이디][Comment.id]가 될 수 있습니다.
 * @param userId 해당 이벤트를 발생시킨 [유저 아이디][User.nickname]
 */
data class Heart(
    val type: HeartType,
    val feedId: String,
    val userId: String,
)

enum class HeartType(
    val index: Int,
    val description: String,
) {
    Feed(0, "피드"),
    Comment(1, "댓글"),
}
