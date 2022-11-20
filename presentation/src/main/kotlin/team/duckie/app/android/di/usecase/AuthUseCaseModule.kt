package team.duckie.app.android.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.domain.repository.AuthRepository
import team.duckie.app.domain.usecase.auth.KakaoLoginUseCase
import team.duckie.app.domain.usecase.auth.LoginUseCase
import team.duckie.app.domain.usecase.auth.SignUpUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object AuthUseCaseModule {

    @Provides
    fun provideKakaoLoginUseCase(
        repository: AuthRepository,
    ): KakaoLoginUseCase {
        return KakaoLoginUseCase(repository = repository)
    }

    @Provides
    fun provideLoginUseCase(
        repository: AuthRepository,
    ): LoginUseCase {
        return LoginUseCase(repository = repository)
    }

    @Provides
    fun provideSignUpUseCase(
        repository: AuthRepository,
    ): SignUpUseCase {
        return SignUpUseCase(repository = repository)
    }
}
