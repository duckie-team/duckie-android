package team.duckie.app.android.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.domain.repository.UserRepository
import team.duckie.app.domain.usecase.user.FetchCategoriesUseCase
import team.duckie.app.domain.usecase.user.GetUserUseCase
import team.duckie.app.domain.usecase.user.HasSessionUseCase
import team.duckie.app.domain.usecase.user.SetUserUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UserUseCaseModule {

    @Provides
    fun provideFetchCategoriesUseCase(
        repository: UserRepository,
    ): FetchCategoriesUseCase {
        return FetchCategoriesUseCase(repository = repository)
    }

    @Provides
    fun provideGetUserUseCase(
        repository: UserRepository,
    ): GetUserUseCase {
        return GetUserUseCase(repository = repository)
    }

    @Provides
    fun provideSetUserUseCase(
        repository: UserRepository,
    ): SetUserUseCase {
        return SetUserUseCase(repository = repository)
    }

    @Provides
    fun provideHasSessionUseCase(
        repository: UserRepository,
    ): HasSessionUseCase {
        return HasSessionUseCase(repository = repository)
    }

}
