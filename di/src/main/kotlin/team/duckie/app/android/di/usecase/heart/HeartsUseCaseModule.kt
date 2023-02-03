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
import team.duckie.app.android.domain.heart.repository.HeartsRepository
import team.duckie.app.android.domain.heart.usecase.HeartsUseCase
import team.duckie.app.android.domain.heart.usecase.UnHeartsUseCase

@Module
@InstallIn(SingletonComponent::class)
object HeartsUseCaseModule {
    @Provides
    fun provideUnHeartsUseCase(repository: HeartsRepository): UnHeartsUseCase {
        return UnHeartsUseCase(repository)
    }

    @Provides
    fun provideHeartsUseCase(repository: HeartsRepository): HeartsUseCase {
        return HeartsUseCase(repository)
    }
}
