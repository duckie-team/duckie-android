/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.repository

import android.content.Context
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository

class RecommendationRepositoryImpl(
    private val context: Context
): RecommendationRepository {
    override suspend fun fetchRecommendations() {
        // TODO(limsaehyun): 비즈니스 로직 작업 필요
    }
}
