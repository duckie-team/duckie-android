package land.sungbin.androidprojecttemplate.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.data.repository.AuthRepositoryImpl
import land.sungbin.androidprojecttemplate.data.repository.GalleryRepositoryImpl
import land.sungbin.androidprojecttemplate.data.repository.NotificationRepositoryImpl
import land.sungbin.androidprojecttemplate.data.repository.UserRepositoryImpl
import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository
import land.sungbin.androidprojecttemplate.domain.repository.GalleryRepository
import land.sungbin.androidprojecttemplate.domain.repository.NotificationRepository
import land.sungbin.androidprojecttemplate.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun provideGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl,
    ): GalleryRepository

    @Binds
    abstract fun provideUserRepository(
        fetchRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    abstract fun provideNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl,
    ): NotificationRepository
}