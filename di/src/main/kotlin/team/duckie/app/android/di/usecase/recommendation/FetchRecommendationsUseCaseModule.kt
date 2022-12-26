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
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase

@Module
@InstallIn(SingletonComponent::class)
object FetchRecommendationsUseCaseModule {

    @Provides
    fun provideFetchRecommendationsUseCase(
        repository: RecommendationRepository
    ): FetchRecommendationsUseCase {
        return FetchRecommendationsUseCase(
            repository = repository,
        )
    }
}
