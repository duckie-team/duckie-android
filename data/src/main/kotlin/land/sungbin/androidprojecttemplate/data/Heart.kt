package land.sungbin.androidprojecttemplate.data

/**
 * [좋아요][Heart] data class
 *
 * @param field 글 필드 값
 * @param ownerId 자신 id
 */
data class Heart(
    val field: String,
    val ownerId: String,
)