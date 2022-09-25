package land.sungbin.androidprojecttemplate.data

/**
 * 팔로우[Follow] data class
 *
 * @param type [FollowType] 구분
 * @param me 본인
 * @param targetUser 타켓 사용자
 */
data class Follow(
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val type: FollowType,
    val me: String,
    val targetUser: String,
)

/** 팔로우 타입 */
enum class FollowType {
    Follow, // 팔로우
    Block,  // 차단
}