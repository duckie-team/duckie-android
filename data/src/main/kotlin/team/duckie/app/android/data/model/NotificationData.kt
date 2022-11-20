package team.duckie.app.android.data.model

import java.util.Date

/**
 * 알림 모델
 * // TODO(riflockle7) 해당 항목은 DB 명세에 없는 항목임. 추후 백앤드에서 해당 내용도 고려 필요!
 *
 * @param type 알림 종류
 * @param id 알림 id
 * @param profileUrl 프로필 URL
 * @param createdAt 알림 생성 일자
 * @param description (정보가 있는 경우) 설명
 * @param targetUserId (상대 유저 정보가 있는 경우) 상대 유저 id
 * @param feedId (피드 알림일 경우) 피드 id
 * @param isFollowed (팔로우 관련 알림인 경우) 팔로우 여부
 * @param duckDealTitle (상품 관련 알림인 경우) 덕딜 제목
 * @param duckDealUrl (상품 관련 알림인 경우) 덕딜 이미지 제목
 */
data class NotificationData(
    val type: String = "",
    val id: Int = 0,
    val profileUrl: String = "",
    val createdAt: Date = Date(),
    val description: String = "",
    val targetUserId: String = "",
    val feedId: String = "",
    val isFollowed: Boolean = false,
    val duckDealTitle: String = "",
    val duckDealUrl: Any = "",
)
