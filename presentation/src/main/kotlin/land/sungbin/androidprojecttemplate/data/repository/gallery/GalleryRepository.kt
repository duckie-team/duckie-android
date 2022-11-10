package land.sungbin.androidprojecttemplate.data.repository.gallery

import land.sungbin.androidprojecttemplate.ui.component.gallery.MediaStoreImage

interface GalleryRepository {

    suspend fun loadImages(): List<MediaStoreImage>
    fun releaseObserver()
}