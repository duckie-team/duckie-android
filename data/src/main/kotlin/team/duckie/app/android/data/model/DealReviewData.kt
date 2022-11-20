package team.duckie.app.android.data.model

internal data class DealReviewData(
    val id: String? = null,
    val buyerId: String? = null,
    val sellerId: String? = null,
    val feedId: String? = null,
    val isDirect: Boolean? = null,
    val review: Int? = null,
    val likeReasons: List<Int>? = null,
    val dislikeReasons: List<Int>? = null,
    val etc: String? = null,
)
