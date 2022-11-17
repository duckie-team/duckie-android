package land.sungbin.androidprojecttemplate.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.domain.repository.SettingRepository
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchAccountInformationUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchSettingUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.score.UpdateSettingUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object ProvideUseCase {

    @Provides
    fun provideFetchSettingUseCase(
        repository: SettingRepository,
    ): FetchSettingUseCase {
        return FetchSettingUseCase(
            repository = repository,
        )
    }

    @Provides
    fun provideUpdateSettingUseCase(
        repository: SettingRepository,
    ): UpdateSettingUseCase {
        return UpdateSettingUseCase(
            repository = repository,
        )
    }

    @Provides
    fun provideFetchAccountInformationUseCase(
        repository: SettingRepository,
    ): FetchAccountInformationUseCase {
        return FetchAccountInformationUseCase(
            repository = repository,
        )
    }
}
