package land.sungbin.androidprojecttemplate.data.repository.gallery

import land.sungbin.androidprojecttemplate.ui.component.gallery.MediaStoreImage

interface GalleryRepository {

    suspend fun loadImages(onRegisterObserver: () -> Unit): List<MediaStoreImage>
    fun releaseObserver()
}