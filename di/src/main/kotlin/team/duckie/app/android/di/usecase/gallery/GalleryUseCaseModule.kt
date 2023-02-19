/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.gallery

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.gallery.repository.GalleryRepository
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase

@Module
@InstallIn(SingletonComponent::class)
object GalleryUseCaseModule {
    @Provides
    fun provideLoadGalleryImagesUseCase(repository: GalleryRepository): LoadGalleryImagesUseCase {
        return LoadGalleryImagesUseCase(repository)
    }
}
