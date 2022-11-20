package team.duckie.app.android.domain.model

import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.model.util.requireInput

/**
 * 신고 모델
 *
 * @param id 고유 아이디
 * @param reporterId 신고자 [유저 아이디][User.nickname]
 * @param targetId 신고당한 [유저 아이디][User.nickname]
 * @param targetContentId 신고당한 컨텐츠 아이디.
 * 유저를 신고했을 경우엔 컨텐츠가 [targetId] 자체가 되므로 null 을 허용합니다.
 * @param message 신고 메시지.
 * **신고 메시지는 공백을 허용하지 않습니다.**
 * @param checked 덕키팀 확인 여부
 */
data class Report(
    @PK val id: String,
    @FK val reporterId: String,
    @FK val targetId: String,
    @FK val targetContentId: String?,
    val message: String,
    val checked: Boolean,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "reporterId",
            value = reporterId,
        )
        requireInput(
            field = "targetId",
            value = targetId,
        )
        requireInput(
            field = "message",
            value = message,
        )
    }
}
