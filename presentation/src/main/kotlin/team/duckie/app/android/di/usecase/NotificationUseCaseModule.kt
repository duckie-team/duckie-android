package team.duckie.app.android.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.domain.repository.NotificationRepository
import team.duckie.app.domain.usecase.fetch.FetchNotificationsUseCase

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
