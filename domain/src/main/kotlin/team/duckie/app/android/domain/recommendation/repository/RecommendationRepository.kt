/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.repository

import androidx.compose.runtime.Immutable
import androidx.paging.PagingSource
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.model.SearchType

/**
 * 홈 화면에서 활용하는 Repository
 *
 * TODO(limsaehyun): 데이터 Return 필요
 */
@Immutable
interface RecommendationRepository {
    fun fetchRecommendations(): PagingSource<Int, RecommendationFeeds.Recommendation>

    suspend fun fetchInitRecommendations()

    suspend fun fetchFollowingTest()

    suspend fun fetchRecommendFollowing()

    suspend fun fetchRecommendTags(
        tag: String,
        type: SearchType,
    )
}
