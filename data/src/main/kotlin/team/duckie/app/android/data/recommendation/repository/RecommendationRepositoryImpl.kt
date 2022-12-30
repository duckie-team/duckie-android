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
) : RecommendationRepository {
    override suspend fun fetchRecommendations() {
        // TODO(limsaehyun): repository 작업 필요
    }

    override suspend fun fetchFollowingTest() {
        // TODO(limsaehyun): repository 작업 필요
    }

    override suspend fun fetchRecommendFollowing() {
        // TODO(limsaehyun): repository 작업 필요
    }

    override suspend fun fetchRecommendTags(tag: String) {
        // TODO(limsaehyun): repository 작업 필요
    }
}
