package team.duckie.app.android.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.domain.repository.GalleryRepository
import team.duckie.app.domain.usecase.gallery.LoadImagesUseCase
import team.duckie.app.domain.usecase.gallery.ReleaseObserverUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object GalleryUseCaseModule {

    @Provides
    fun provideLoadImagesUseCase(
        repository: GalleryRepository,
    ): LoadImagesUseCase {
        return LoadImagesUseCase(repository = repository)
    }

    @Provides
    fun provideReleaseObserverUseCase(
        repository: GalleryRepository,
    ): ReleaseObserverUseCase {
        return ReleaseObserverUseCase(repository = repository)
    }
}
