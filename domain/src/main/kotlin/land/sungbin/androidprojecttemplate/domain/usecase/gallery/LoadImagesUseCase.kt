package land.sungbin.androidprojecttemplate.domain.usecase.gallery

import land.sungbin.androidprojecttemplate.domain.repository.GalleryRepository

class LoadImagesUseCase(
    private val repository: GalleryRepository
) {
    suspend operator fun invoke() = runCatching {
        repository.loadImages()
    }
}