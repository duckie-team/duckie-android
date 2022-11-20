package team.duckie.app.android.domain.usecase.gallery

import team.duckie.app.domain.repository.GalleryRepository

class LoadImagesUseCase(
    private val repository: GalleryRepository
) {
    suspend operator fun invoke() = runCatching {
        repository.loadImages()
    }
}
