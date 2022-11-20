package team.duckie.app.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.domain.repository.SettingRepository
import team.duckie.app.domain.usecase.fetch.FetchAccountInformationUseCase
import team.duckie.app.domain.usecase.fetch.FetchSettingUseCase
import team.duckie.app.domain.usecase.score.UpdateSettingUseCase

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
