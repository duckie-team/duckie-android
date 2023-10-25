/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.repository

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.RecommendationItem

/** 홈 화면에서 활용하는 Repository */
@Immutable
interface RecommendationRepository {
    fun fetchRecommendations(): Flow<PagingData<RecommendationItem>>

    fun fetchMusicRecommendations(): Flow<PagingData<RecommendationItem>>

    suspend fun fetchJumbotrons(): ImmutableList<Exam>

    suspend fun fetchMusicJumbotrons(): ImmutableList<Exam>
}
