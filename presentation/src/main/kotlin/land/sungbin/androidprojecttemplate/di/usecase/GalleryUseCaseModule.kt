package land.sungbin.androidprojecttemplate.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.domain.repository.GalleryRepository
import land.sungbin.androidprojecttemplate.domain.usecase.gallery.LoadImagesUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.gallery.ReleaseObserverUseCase

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