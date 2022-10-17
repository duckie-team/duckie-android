package land.sungbin.androidprojecttemplate.data.model

internal data class DealReviewData(
    val id: String? = null,
    val buyerId: String? = null,
    val sellerId: String? = null,
    val feedId: String? = null,
    val isDirect: Boolean? = null,
    val review: Int? = null,
    val likeReason: List<Int>? = null,
    val dislikeReason: List<Int>? = null,
    val etc: String? = null,
)
