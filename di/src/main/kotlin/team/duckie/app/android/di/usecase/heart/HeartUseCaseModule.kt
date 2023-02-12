/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.heart

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.heart.repository.HeartRepository
import team.duckie.app.android.domain.heart.usecase.PostHeartUseCase
import team.duckie.app.android.domain.heart.usecase.DeleteHeartUseCase

@Module
@InstallIn(SingletonComponent::class)
object HeartUseCaseModule {
    @Provides
    fun provideUnHeartUseCase(repository: HeartRepository): DeleteHeartUseCase {
        return DeleteHeartUseCase(repository)
    }

    @Provides
    fun provideHeartUseCase(repository: HeartRepository): PostHeartUseCase {
        return PostHeartUseCase(repository)
    }
}
