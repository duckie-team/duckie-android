package land.sungbin.androidprojecttemplate.data

/**
 * [덕피드][DuckFeed] data class
 *
 * @param field 필드 값
 * @param writer 글쓴이
 * @param isDeleted 삭제 여부
 * @param content 본문
 * @param pictures 사진 목록
 * @param videos 영상 목록
 * @param category 카테고리
 * @param tags 태그 목록
 */
data class DuckFeed(
    val field: String,
    val writer: String,
    val isDeleted: Boolean,
    val content: String,
    val pictures: List<String>,
    val videos: List<String>,
    val category: Int,
    val tags: List<String>,
)