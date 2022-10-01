package land.sungbin.androidprojecttemplate.data

/**
 * 채팅[Chat] data class
 *
 * @param isDeleted 삭제 여부
 * @param roomNumber 방 번호
 * @param writer 글쓴이
 * @param message 채팅 내용
 * @param createdAt 쓴 채팅 및 시간
 * @param deletedAt 삭제한 채팅 및 시간
 */
data class Chat(
    val isDeleted: Boolean,
    val roomNumber: String,
    val writer: String,
    val message: ContentMessage,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val createdAt: String,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val deletedAt: String,
);