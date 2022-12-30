/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.recommendation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendFollowingUseCase

@Module
@InstallIn(SingletonComponent::class)
object FetchRecommendFollowingUseCaseModule {

    @Provides
    fun provideFetchRecommendFollowingUseCase(
        repository: RecommendationRepository
    ): FetchRecommendFollowingUseCase {
        return FetchRecommendFollowingUseCase(
            repository = repository,
        )
    }
}
