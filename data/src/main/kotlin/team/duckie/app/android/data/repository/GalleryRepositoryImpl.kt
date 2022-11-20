package team.duckie.app.android.data.repository

import team.duckie.app.android.data.datasource.local.GalleryDatasource
import team.duckie.app.domain.model.MediaStoreImage
import team.duckie.app.domain.repository.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val galleryDatasource: team.duckie.app.android.data.datasource.local.GalleryDatasource,
) : GalleryRepository {

    override suspend fun loadImages(): List<MediaStoreImage> = galleryDatasource.queryImages()

    override fun releaseObserver() = galleryDatasource.unregisterContentObserver()
}
