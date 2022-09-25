package land.sungbin.androidprojecttemplate.data

/**
 * 채팅방[ChatRoom] data class
 *
 * @param isDeleted 삭제 여부
 * @param chatRoomType [ChatRoomType] 채팅 유형
 * @param chatField 채팅방 필드 값
 * @param chatCategory [LikeCategory] 카테고리
 * @param tags 태그 목록
 */
data class ChatRoom(
    val isDeleted: Boolean,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val chatRoomType: ChatRoomType,
    val chatField: String,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val chatCategory: LikeCategory,
    val tags: List<String>,
)

enum class ChatRoomType {
    Chat,
    DuckDeal,
    Open,
}