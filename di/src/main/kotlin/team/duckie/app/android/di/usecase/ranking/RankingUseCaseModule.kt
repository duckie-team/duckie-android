/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.ranking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.ranking.repository.RankingRepository
import team.duckie.app.android.domain.ranking.usecase.GetExamRankingsByAnswerRate
import team.duckie.app.android.domain.ranking.usecase.GetExamRankingsBySolvedCount
import team.duckie.app.android.domain.ranking.usecase.GetUserRankingsUseCase

@Module
@InstallIn(SingletonComponent::class)
object RankingUseCaseModule {
    @Provides
    fun provideGetUserRankingUseCase(repository: RankingRepository): GetUserRankingsUseCase {
        return GetUserRankingsUseCase(repository)
    }

    @Provides
    fun provideGetExamRankingsByAnswerRate(repository: RankingRepository): GetExamRankingsByAnswerRate {
        return GetExamRankingsByAnswerRate(repository)
    }

    @Provides
    fun provideGetExamRankingsBySolvedCount(repository: RankingRepository): GetExamRankingsBySolvedCount {
        return GetExamRankingsBySolvedCount(repository)
    }
}
