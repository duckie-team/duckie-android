package land.sungbin.androidprojecttemplate.domain.model.common

import androidx.annotation.Size
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireSize

/**
 * 공통되는 내용을 담는 모델
 *
 * @param text 본문 텍스트.
 * 본문 텍스트는 공백일 수 있습니다.
 * @param images 이미지 주소 목록.
 * 최대 5개를 받고 한 이미지당 최대 5 MB 를 넘을 수 없습니다.
 * (한 이미지당 최대 용량은 조정 필요)
 * @param video 동영상 주소.
 * 업로드된 동영상이 없을 경우 null 을 받습니다.
 * 최대 100 MB 를 넘을 수 없습니다. (조정 필요)
 */
data class Content(
    val text: String,
    @Size(min = 0, max = 5) val images: List<String>,
    @property:Unsupported val video: String? = null,
) {
    init {
        requireSize(
            max = 5,
            field = "images",
            value = images,
        )
    }
}
