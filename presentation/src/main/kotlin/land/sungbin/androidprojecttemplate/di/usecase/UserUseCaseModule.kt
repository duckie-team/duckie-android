package land.sungbin.androidprojecttemplate.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.domain.repository.UserRepository
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchCategoriesUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UserUseCaseModule {

    @Provides
    fun provideFetchCategoriesUseCase(
        repository: UserRepository,
    ): FetchCategoriesUseCase {
        return FetchCategoriesUseCase(repository = repository)
    }
}