package team.duckie.app.android.domain.gallery.repository

import androidx.compose.runtime.Immutable

/**
 * 갤러리 관련 작업을 하는 repository 입니다.
 */
@Immutable
interface GalleryRepository {
    /**
     * MediaStore 에서 이미지 목록을 불러옵니다.
     *
     * @return MediaStore 에서 불러온 이미지들의 Uri 목록.
     * android 의존성을 없애기 위해 [android.net.Uri] 로 값을 불러오지 않고,
     * String 으로 불러옵니다.
     */
    fun loadImages(): List<String>
}
