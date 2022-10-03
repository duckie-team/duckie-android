package land.sungbin.androidprojecttemplate.data

/**
 * [구매자 거래후기][BuyerReview] data class
 *
 * @param buyerId 구매자 id
 * @param sellerId 판매자 id
 * @param isDirect 거래방식 (= 직거래인지, 아니라면 택배거래)
 * @param saleState [SaleState] 거래 간단 상태
 * @param likeReason [LikeReason] 좋은 점
 * @param dislikeReason [DislikeReason] 아쉬운 점
 * @param etc 기타 (string)
 */
data class BuyerReview(
    val buyerId: String,
    val sellerId: String,
    val isDirect: Boolean,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val saleState: SaleState,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val likeReason: LikeReason,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val dislikeReason: DislikeReason,
    val etc: String,
)

/** 거래 간단 상태 */
enum class SaleState {
    Sorry,  // 아쉬워요
    Like,   // 좋아요
    Great,  // 최고예요
}

/** 좋은 점 (사유) */
enum class LikeReason {
    KeepTime,               // 시간약속을 잘 지켜요
    FullManner,             // 친절하고 매너가 좋아요
    ReplyFast,              // 답장이 빨라요
    MoreCheap,              // 시세보다 가격이 저렴해요
    // TODO (riflockle7) 보다 좋은 워딩 추천 부탁드립니다.
    StateEqualDescription,  // 상품 상태가 설명과 같아요
    ShippingFast,           // 발송이 빨라요
    PackagingMeticulous,    // 택배 포장이 꼼꼼해요
}

/** 아쉬운 점 (사유) */
enum class DislikeReason {
    BreakTime,                  // 시간약속을 안지켜요
    NoManner,                   // 불친절하고 매너가 없어요
    ReplySlow,                  // 답장이 느려요
    LateTimeCall,               // 너무 늦은 시간에 연락해요
    StateNotEqualDescription,   // 상품상태가 설명과 달라요
    NoShow,                     // 약속장소에 나오지 않았어요
}