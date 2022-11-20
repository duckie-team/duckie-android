package team.duckie.app.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.data.repository.AuthRepositoryImpl
import team.duckie.app.data.repository.GalleryRepositoryImpl
import team.duckie.app.data.repository.NotificationRepositoryImpl
import team.duckie.app.data.repository.UserRepositoryImpl
import team.duckie.app.domain.repository.AuthRepository
import team.duckie.app.domain.repository.GalleryRepository
import team.duckie.app.domain.repository.NotificationRepository
import team.duckie.app.domain.repository.UserRepository

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
