/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.follows

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.follow.repository.FollowsRepository
import team.duckie.app.android.domain.follow.usecase.FollowUseCase

@Module
@InstallIn(SingletonComponent::class)
object FollowsUseCaseModule {
    @Provides
    fun provideFollowUseCase(repository: FollowsRepository): FollowUseCase {
        return FollowUseCase(repository)
    }
}
