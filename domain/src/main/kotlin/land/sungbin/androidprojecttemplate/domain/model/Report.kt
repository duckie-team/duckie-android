package land.sungbin.androidprojecttemplate.domain.model

/**
 * 신고 모델
 *
 * @param id 고유 아이디
 * @param reporterId 신고자 [유저 아이디][User.nickname]
 * @param targetId 신고당한 [유저 아이디][User.nickname]
 * @param targetFeedId 신고당한 [피드 아이디][Feed.id].
 * 꼭 피드를 신고할 것이라는 보장이 없으므로 null 을 허용합니다.
 * @param message 신고 메시지
 * @param checked 덕키팀 확인 여부
 */
data class Report(
    val id: String,
    val reporterId: String,
    val targetId: String,
    val targetFeedId: String?,
    val message: String,
    val checked: Boolean,
)
