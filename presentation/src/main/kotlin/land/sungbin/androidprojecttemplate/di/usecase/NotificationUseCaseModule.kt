package land.sungbin.androidprojecttemplate.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.domain.repository.NotificationRepository
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchNotificationsUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object NotificationUseCaseModule {

    @Provides
    fun provideFetchNotificationsUseCase(
        repository: NotificationRepository,
    ): FetchNotificationsUseCase {
        return FetchNotificationsUseCase(repository = repository)
    }
}