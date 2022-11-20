package team.duckie.app.android.domain.model

import androidx.annotation.Size
import team.duckie.app.domain.model.constraint.DislikeReason
import team.duckie.app.domain.model.constraint.LikeReason
import team.duckie.app.domain.model.constraint.ReasonToken
import team.duckie.app.domain.model.constraint.Review
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.model.util.requireInput
import team.duckie.app.domain.model.util.requireSize

/**
 * 거래 후기 모델
 *
 * @param id 고유 아이디
 * @param buyerId 구매자 [유저 아이디][User.nickname]
 * @param sellerId 판매자 [유저 아이디][User.nickname]
 * @param feedId 해당 거래가 진행된 [덕피드 아이디][Feed.id]
 * @param isDirect 직거래인지 여부
 * @param review 거래에 대한 종합적인 리뷰
 * @param likeReasons 좋았던 점 목록
 * @param dislikeReasons 아쉬웠던 점 목록
 * @param etc 기타 소견. 기본값은 null 이며, 공백일 수 있습니다.
 */
data class DealReview(
    @PK val id: String,
    @FK val buyerId: String,
    @FK val sellerId: String,
    @FK val feedId: String,
    val isDirect: Boolean,
    val review: Review,
    @Size(min = 1) val likeReasons: List<LikeReason>,
    @Size(min = 1) val dislikeReasons: List<DislikeReason>,
    val etc: String? = null,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "buyerId",
            value = buyerId,
        )
        requireInput(
            field = "sellerId",
            value = sellerId,
        )
        requireInput(
            field = "feedId",
            value = feedId,
        )
        requireSize(
            min = 1,
            field = "likeReason",
            value = likeReasons,
        )
        requireSize(
            min = 1,
            field = "dislikeReason",
            value = dislikeReasons,
        )
        if (
            likeReasons.any { reason ->
                reason.token == ReasonToken.Buyer
            } &&
            likeReasons.any { reason ->
                reason.token == ReasonToken.Seller
            }
        ) {
            throw IllegalArgumentException("Buyer and Seller cannot be selected at the same time.")
        }
        if (
            dislikeReasons.any { reason ->
                reason.token == ReasonToken.Buyer
            } &&
            dislikeReasons.any { reason ->
                reason.token == ReasonToken.Seller
            }
        ) {
            throw IllegalArgumentException("Buyer and Seller cannot be selected at the same time.")
        }
    }
}

