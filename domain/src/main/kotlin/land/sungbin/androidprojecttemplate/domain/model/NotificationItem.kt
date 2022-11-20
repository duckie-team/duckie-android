package land.sungbin.androidprojecttemplate.domain.model

import android.app.Notification
import java.util.Date

/**
 * 알림 목록의 항목 (Android 에서 제공하는 [Notification] 과 이름 차이를 두기 위해 이렇게 네이밍 함)
 * 각 타입의 공통된 데이터를 하나로 묶어 처리함
 *
 * @param id 알림 id
 * @param profileUrl 프로필 URL
 * @param createdAt 작성일자
 */
sealed class NotificationItem(
    open val id: Int,
    open val type: NotificationType,
    open val profileUrl: Any,
    open val createdAt: Date,
) {
    /**
     * 타 유저가, 내 글에 댓글을 달음
     *
     * @param description 내용
     * @param targetUserId 타 유저 id
     * @param feedId 이동할 피드 id
     */
    data class NewComment(
        override val id: Int,
        override val profileUrl: Any,
        override val createdAt: Date,
        val description: String,
        val targetUserId: String,
        val feedId: String,
    ) : NotificationItem(id, NotificationType.NewComment, profileUrl, createdAt)

    /**
     * 타 유저가, 내 글에 좋아요를 누름
     *
     * @param description 내용
     * @param targetUserId 타 유저 id
     * @param feedId 이동할 피드 id
     */
    data class NewHeart(
        override val id: Int,
        override val profileUrl: Any,
        override val createdAt: Date,
        val description: String,
        val targetUserId: String,
        val feedId: String,
    ) : NotificationItem(id, NotificationType.NewHeart, profileUrl, createdAt)

    /**
     * 타 유저가, 나를 팔로우함
     *
     * @param targetUserId 타 유저 id
     * @param isFollowed 팔로우 여부
     */
    data class NewFollower(
        override val id: Int,
        override val profileUrl: Any,
        override val createdAt: Date,
        val targetUserId: String,
        val isFollowed: Boolean,
    ) : NotificationItem(id, NotificationType.NewFollower, profileUrl, createdAt)

    /**
     * 서비스가, 내가 구매한 물품에 거래 후기를 남기라고 요청함
     *
     * @param duckDealTitle 덕딜 상품 이름
     * @param duckDealUrl 덕딜 상품 URL
     */
    data class RequireWriteReview(
        override val id: Int,
        override val profileUrl: Any,
        override val createdAt: Date,
        val duckDealTitle: String,
        val duckDealUrl: Any,
        // TODO(riflockle7) 어떤 동작을 하는지에 따라 필요한 데이터 추가 필요
    ) : NotificationItem(id, NotificationType.RequireWriteReview, profileUrl, createdAt)

    /**
     * 서비스가, 예약중인 상태의 상품을 거래 완료로 바꾸라고 요청함
     *
     * @param duckDealTitle 덕딜 상품 이름
     * @param duckDealUrl 덕딜 상품 URL
     */
    data class RequireChangeDealState(
        override val id: Int,
        override val profileUrl: Any,
        override val createdAt: Date,
        val duckDealTitle: String,
        val duckDealUrl: Any,
        // TODO(riflockle7) 어떤 동작을 하는지에 따라 필요한 데이터 추가 필요
    ) : NotificationItem(id, NotificationType.RequireChangeDealState, profileUrl, createdAt)

    /**
     * 타 유저가, 내 피드에 판매 요청을 함
     *
     * @param targetUserId 타 유저 id
     * @param duckDealUrl 덕딜 상품 URL
     */
    data class RequireUpToDuckDeal(
        override val id: Int,
        override val profileUrl: Any,
        override val createdAt: Date,
        val targetUserId: String,
        val duckDealUrl: Any,
        // TODO(riflockle7) 어떤 동작을 하는지에 따라 필요한 데이터 추가 필요
    ) : NotificationItem(id, NotificationType.RequireUpToDuckDeal, profileUrl, createdAt)
}

enum class NotificationType(val key: String) {
    NewComment("NewComment"),
    NewHeart("NewHeart"),
    NewFollower("NewFollower"),
    RequireWriteReview("RequireWriteReview"),
    RequireChangeDealState("RequireChangeDealState"),
    RequireUpToDuckDeal("RequireUpToDuckDeal"),
}