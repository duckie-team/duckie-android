@file:Suppress("unused")

package team.duckie.app.android.domain.model.constraint

/**
 * 거래 상태
 *
 * @param index 실제 API 요청에 사용됩니다.
 * 각각 리뷰 값은 서버에 [int][Int] 형식으로 저장되며, 이 값을 나타냅니다.
 * @param description 해당 리뷰를 텍스트 형식으로 나타냅니다.
 * presentation 에서 하드코딩을 막기 위함과 각각 리뷰 값 설명을 위해 추가됐습니다.
 * presentation 에서는 아래와 같이 사용될 수 있습니다.
 *
 * ```kotlin
 * val reviews = Review.values().map(Review::description)
 * // 위 값으로 리뷰 아이템들을 구성하면 일일이 리뷰 값들을 하드코딩할 필요가 없습니다.
 * ```
 */
enum class Review(
    val index: Int,
    val description: String,
) {
    Bad(
        index = 0,
        description = "아쉬워요",
    ),
    Good(
        index = 1,
        description = "좋아요",
    ),
    Awesome(
        index = 2,
        description = "최고예요",
    ),
}

/**
 * 사유 토큰.
 * 이 값은 domain 모듈에서만 assertion 용도로 사용됩니다.
 *
 * 사유마다 각각 사용 가능한 상황이 있기 때문에 해당 상황을
 * 구분하기 위해 사용됩니다.
 *
 * @property All 구매자와 판매자 모두에게 해당합니다.
 * @property Buyer 구매자가 판매자에게 보내는 상황에 해당합니다.
 * @property Seller 판매자가 구매자에게 보내는 상황에 해당합니다.
 */
internal enum class ReasonToken(
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

/**
 * 좋은 점 (사유)
 *
 * @param index 실제 API 요청에 사용됩니다.
 * 각각 사유 값은 서버에 [int][Int] 형식으로 저장되며, 이 값을 나타냅니다.
 * @param description 해당 사유를 텍스트 형식으로 나타냅니다.
 * presentation 에서 하드코딩을 막기 위함과 각각 사유 값 설명을 위해 추가됐습니다.
 * presentation 에서는 아래와 같이 사용될 수 있습니다.
 *
 * ```kotlin
 * val likeReasons = LikeReason.values().map(LikeReason::description)
 * // 위 값으로 사유 아이템들을 구성하면 일일이 사유 값들을 하드코딩할 필요가 없습니다.
 * ```
 * @param token **이 값은 domain 모듈에서만 assertion 용도로 사용됩니다.**
 * 사유마다 각각 사용 가능한 상황이 있기 때문에 해당 상황을 구분하기 위해 사용됩니다.
 */
enum class LikeReason(
    val index: Int,
    val description: String,
    internal val token: ReasonToken = ReasonToken.All,
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

/**
 * 아쉬운 점 (사유)
 *
 * @param index 실제 API 요청에 사용됩니다.
 * 각각 사유 값은 서버에 [int][Int] 형식으로 저장되며, 이 값을 나타냅니다.
 * @param description 해당 사유를 텍스트 형식으로 나타냅니다.
 * presentation 에서 하드코딩을 막기 위함과 각각 사유 값 설명을 위해 추가됐습니다.
 * presentation 에서는 아래와 같이 사용될 수 있습니다.
 *
 * ```kotlin
 * val dislikeReasons = DislikeReason.values().map(DislikeReason::description)
 * // 위 값으로 사유 아이템들을 구성하면 일일이 사유 값들을 하드코딩할 필요가 없습니다.
 * ```
 * @param token **이 값은 domain 모듈에서만 assertion 용도로 사용됩니다.**
 * 사유마다 각각 사용 가능한 상황이 있기 때문에 해당 상황을 구분하기 위해 사용됩니다.
 */
enum class DislikeReason(
    val index: Int,
    val description: String,
    internal val token: ReasonToken = ReasonToken.All,
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
