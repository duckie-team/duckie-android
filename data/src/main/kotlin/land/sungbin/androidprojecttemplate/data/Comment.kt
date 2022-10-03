package land.sungbin.androidprojecttemplate.data

/**
 * [댓글][Comment] data class
 *
 * @param isDeleted 댓글 삭제 여부
 * @param field 댓글 필드 값
 * @param document 댓글 도큐먼트
 * @param ownerId 자신 id
 * @param description 댓글 내용
 * @param sentAt 보낸 날짜 및 시간 (yyyy-MM-dd-hh-mm-ss)
 * @param deletedAt 삭제한 날짜 및 시간 (yyyy-MM-dd-hh-mm-ss)
 */
data class Comment(
    val isDeleted: Boolean,
    val field: String,
    val document: String,
    val ownerId: String,
    val description: ContentMessage,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val sentAt: String,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val deletedAt: String,
)
