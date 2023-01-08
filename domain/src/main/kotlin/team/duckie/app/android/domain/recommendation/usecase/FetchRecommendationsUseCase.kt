/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.usecase

import androidx.compose.runtime.Immutable
import androidx.paging.PagingSource
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import javax.inject.Inject

@Immutable
class FetchRecommendationsUseCase @Inject constructor(
    private val repository: RecommendationRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.fetchRecommendations()
    }
}
