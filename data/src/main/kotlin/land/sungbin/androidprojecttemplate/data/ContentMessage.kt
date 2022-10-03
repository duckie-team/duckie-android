package land.sungbin.androidprojecttemplate.data

/**
 * [채팅 메시지][ContentMessage] data class
 *
 * @param text 텍스트 내용 (없을 경우 null)
 * @param images 이미지 URL 목록 (없을 경우 null)
 * @param video 비디오 URL (없을 경우 null)
 */
data class ContentMessage(
    val text: String?,
    val images: List<String>?,
    val video: String?,
)