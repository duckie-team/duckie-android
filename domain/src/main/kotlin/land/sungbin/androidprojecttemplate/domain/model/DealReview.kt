package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.Size
import land.sungbin.androidprojecttemplate.domain.model.ReasonToken.All
import land.sungbin.androidprojecttemplate.domain.model.ReasonToken.Buyer
import land.sungbin.androidprojecttemplate.domain.model.ReasonToken.Seller
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireSize

/**
 * 거래 후기 모델
 *
 * @param id 고유 아이디
 * @param buyerId 구매자 [유저 아이디][User.nickname]
 * @param sellerId 판매자 [유저 아이디][User.nickname]
 * @param feedId 해당 거래가 진행된 [덕피드 아이디][Feed.id]
 * @param isDirect 직거래인지 여부
 * @param review 거래에 대한 종합적인 리뷰
 * @param likeReason 좋았던 점 목록
 * @param dislikeReason 아쉬웠던 점 목록
 * @param etc 기타 소견.
 * 기타 소견은 공백일 수 있습니다.
 */
data class DealReview(
    val id: String,
    val buyerId: String,
    val sellerId: String,
    val feedId: String,
    val isDirect: Boolean,
    val review: Review,
    @Size(min = 1) val likeReason: List<LikeReason>,
    @Size(min = 1) val dislikeReason: List<DislikeReason>,
    val etc: String,
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
        requireInput(
            field = "etc",
            value = etc,
        )
        requireSize(
            min = 1,
            field = "likeReason",
            value = likeReason,
        )
        requireSize(
            min = 1,
            field = "dislikeReason",
            value = dislikeReason,
        )
    }
}

/** 거래 상태 */
enum class Review(
    val index: Int,
    val description: String,
) {
    Bad(
        index = 0,
        description = "아쉬워요",
    ),
    Like(
        index = 1,
        description = "좋아요",
    ),
    Awesome(
        index = 2,
        description = "최고예요",
    ),
}

/**
 * 사유 토큰
 *
 * 사유마다 각각 사용 가능한 상황이 있기 때문에
 * 해당 상황을 구분하기 위해 사용됩니다.
 *
 * @property All 구매자와 판매자 모두에게 해당합니다.
 * @property Buyer 구매자가 판매자에게 보내는 상황에 해당합니다.
 * @property Seller 판매자가 구매자에게 보내는 상황에 해당합니다.
 */
enum class ReasonToken(
    val index: Int,
    val description: String,
) {
    All(
        index = 0,
        description = "모두",
    ),
    Buyer(
        index = 1,
        description = "구매자",
    ),
    Seller(
        index = 2,
        description = "판매자",
    ),
}

/** 좋은 점 (사유) */
enum class LikeReason(
    val index: Int,
    val description: String,
    val token: ReasonToken = All,
) {
    KeepTime(
        index = 0,
        description = "시간약속을 잘 지켜요",
    ),
    NiceMannger(
        index = 1,
        description = "친절하고 매너가 좋아요",
    ),
    ReplyFast(
        index = 2,
        description = "답장이 빨라요",
    ),
    MoreCheap(
        index = 3,
        description = "시세보다 가격이 저렴해요",
        token = ReasonToken.Buyer,
    ),
    StateSame(
        index = 4,
        description = "상품 상태가 설명과 같아요",
        token = ReasonToken.Buyer,
    ),
    ShippingFast(
        index = 5,
        description = "발송이 빨라요",
        token = ReasonToken.Buyer,
    ),
    PackagingMeticulous(
        index = 6,
        description = "택배 포장이 꼼꼼해요",
        token = ReasonToken.Buyer,
    ),
}

/** 아쉬운 점 (사유) */
enum class DislikeReason(
    val index: Int,
    val description: String,
    val token: ReasonToken = All,
) {
    BreakTime(
        index = 0,
        description = "시간약속을 안지켜요",
    ),
    NoManner(
        index = 1,
        description = "불친절하고 매너가 없어요",
    ),
    ReplySlow(
        index = 2,
        description = "답장이 느려요",
    ),
    LateTimeCall(
        index = 3,
        description = "너무 늦은 시간에 연락해요",
    ),
    NoShow(
        index = 4,
        description = "약속장소에 나오지 않았어요",
    ),
    HardLoweringPrice(
        index = 5,
        description = "무리하게 가격을 깎아요",
        token = ReasonToken.Seller,
    ),
    StateNotSame(
        index = 6,
        description = "상품상태가 설명과 달라요",
        token = ReasonToken.Buyer,
    ),
}
