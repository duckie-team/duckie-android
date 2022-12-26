/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.repository

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.recommendation.repository.RecommendationRepositoryImpl
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository

@Module
@InstallIn(SingletonComponent::class)
object RecommendationRepositoryModule {

    @Provides
    fun provideRecommendationRepository(
        @ApplicationContext context: Context,
    ): RecommendationRepository {
       return RecommendationRepositoryImpl(context)
    }
}
