package land.sungbin.androidprojecttemplate.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.domain.repository.UserRepository
import land.sungbin.androidprojecttemplate.domain.usecase.user.FetchCategoriesUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.user.GetUserUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.user.HasSessionUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.user.SetUserUseCase

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