package land.sungbin.androidprojecttemplate.data

/**
 * 좋아요[Heart] data class
 *
 * @param isLiked 좋아요 여부
 * @param duckContentField 글 필드 값
 * @param myId 자신 id
 */
data class Heart(
    val isLiked: Boolean,
    val duckContentField: String,
    val myId: String,
)