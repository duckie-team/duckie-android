package land.sungbin.androidprojecttemplate.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.data.repository.AuthRepositoryImpl
import land.sungbin.androidprojecttemplate.data.repository.GalleryRepositoryImpl
import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository
import land.sungbin.androidprojecttemplate.domain.repository.GalleryRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun provideGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository
}