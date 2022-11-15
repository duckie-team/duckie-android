package land.sungbin.androidprojecttemplate.domain.usecase.gallery

import land.sungbin.androidprojecttemplate.domain.repository.GalleryRepository

class ReleaseObserverUseCase(
    private val repository: GalleryRepository
) {
    operator fun invoke() = runCatching {
        repository.releaseObserver()
    }
}