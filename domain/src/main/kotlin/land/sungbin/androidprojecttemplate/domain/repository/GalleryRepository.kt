package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.MediaStoreImage

interface GalleryRepository {

    suspend fun loadImages(): List<MediaStoreImage>
    fun releaseObserver()
}