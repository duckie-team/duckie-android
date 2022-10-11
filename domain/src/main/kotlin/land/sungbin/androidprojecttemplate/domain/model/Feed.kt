package land.sungbin.androidprojecttemplate.domain.model

import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories

/**
 * 피드 모델
 *
 * @param id 피드 고유 아이디
 * @param writerId 작성자 아이디
 * @param type 피드 타입
 * @param isDeleted 삭제됐는지 여부
 * @param isHidden 숨김처리 됐는지 여부
 * @param content 피드 내용
 * @param categories 피드 카테고리
 * @param createdAt 피드 생성 시간
 * @param title 상품명.
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 * @param price 상품 가격.
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 * @param location 직거래 위치.
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 * @param isDirectDealing 직거래 여부.
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 * @param parcelable 택배 거래 여부.
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 * @param dealState 거래 상태.
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 */
data class Feed(
    val id: String,
    val writerId: String,
    val type: FeedType,
    val isDeleted: Boolean,
    val isHidden: Boolean,
    val content: Content,
    val categories: Categories,
    val createdAt: Date,
    val title: String?,
    val price: Int?,
    val location: String?,
    val isDirectDealing: Boolean?,
    val parcelable: Boolean?,
    val dealState: DealState?,
)

/** 피드 타입 */
enum class FeedType(
    val index: Int,
    val description: String,
) {
    DuckDeal(0, "덕딜"),
    Normal(1, "덕피드"),
}

/** 거래 상태 */
enum class DealState(
    val index: Int,
    val description: String,
) {
    InProgress(0, "거래중"),
    Booking(1, "예약중"),
    Done(2, "거래완료"),
}
