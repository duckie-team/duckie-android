package team.duckie.app.android.domain.repository

import team.duckie.app.domain.model.MediaStoreImage

interface GalleryRepository {
    suspend fun loadImages(): List<MediaStoreImage>
    fun releaseObserver()
}
