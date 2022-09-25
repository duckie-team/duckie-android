package land.sungbin.androidprojecttemplate.data

/**
 * 댓글[Comment] data class
 *
 * @param isDeleted 댓글 삭제 여부
 * @param commentField 댓글 필드 값
 * @param document 댓글 도큐먼트
 * @param myId 자신 id
 * @param description 내용
 * @param sentTime 보낸 날짜 및 시간 (yyyy-MM-dd-hh-mm-ss)
 */
data class Comment(
    val isDeleted: Boolean,
    val commentField: String,
    val document: String,
    val myId: String,
    val description: String,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val sentTime: String,
)