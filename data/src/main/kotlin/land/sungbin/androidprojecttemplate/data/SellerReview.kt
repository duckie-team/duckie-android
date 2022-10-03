package land.sungbin.androidprojecttemplate.data

/**
 * [판매자 거래후기][SellerReview] data class
 *
 * @param buyerId 구매자 id
 * @param sellerId 판매자 id
 * @param isDirect 거래방식 (= 직거래인지, 아니라면 택배거래)
 * @param saleState [SaleState] 거래 간단 상태
 * @param likeReason [LikeReason] 좋은 점
 * @param dislikeReason [DislikeReason] 아쉬운 점
 * @param etc 기타 (string)
 */
data class SellerReview(
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