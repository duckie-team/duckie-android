package land.sungbin.androidprojecttemplate.data.repository

import land.sungbin.androidprojecttemplate.data.datasource.local.GalleryDatasource
import land.sungbin.androidprojecttemplate.domain.model.MediaStoreImage
import land.sungbin.androidprojecttemplate.domain.repository.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val galleryDatasource: GalleryDatasource,
) : GalleryRepository {

    override suspend fun loadImages(): List<MediaStoreImage> = galleryDatasource.queryImages()

    override fun releaseObserver() = galleryDatasource.unregisterContentObserver()
}