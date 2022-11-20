package team.duckie.app.android.domain.usecase.gallery

import team.duckie.app.domain.repository.GalleryRepository

class ReleaseObserverUseCase(
    private val repository: GalleryRepository
) {
    operator fun invoke() = runCatching {
        repository.releaseObserver()
    }
}
