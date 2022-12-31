/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.auth.repository.AuthRepository
import team.duckie.app.android.domain.auth.usecase.CheckAccessTokenUseCase
import team.duckie.app.android.domain.auth.usecase.JoinUseCase

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {
    @Provides
    fun provideCheckAccessTokenUseCase(repository: AuthRepository): CheckAccessTokenUseCase {
        return CheckAccessTokenUseCase(repository)
    }

    @Provides
    fun provideJoinUseCase(repository: AuthRepository): JoinUseCase {
        return JoinUseCase(repository)
    }
}
