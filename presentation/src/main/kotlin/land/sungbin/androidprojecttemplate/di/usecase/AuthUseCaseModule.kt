package land.sungbin.androidprojecttemplate.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository
import land.sungbin.androidprojecttemplate.domain.usecase.auth.KakaoLoginUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.auth.LoginUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.auth.SignUpUseCase

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