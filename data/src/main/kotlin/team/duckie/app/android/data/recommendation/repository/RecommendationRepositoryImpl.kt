/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.repository

import android.content.Context
import androidx.paging.PagingSource
import team.duckie.app.android.data.recommendation.paging.RecommendationPagingSource
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository

@Suppress("UnusedPrivateMember") // TODO(limsaehyun) context를 추후에 활용하기 위함
class RecommendationRepositoryImpl(
    private val context: Context,
) : RecommendationRepository {
    override fun fetchRecommendations(): PagingSource<Int, RecommendationFeeds.Recommendation> {
        return RecommendationPagingSource()
    }

    override suspend fun fetchJumbotrons() {
        // TODO(limsaehyun): repository 작업 필요
    }

    override suspend fun fetchFollowingTest() {
        // TODO(limsaehyun): repository 작업 필요
    }

    override suspend fun fetchRecommendFollowing() {
        // TODO(limsaehyun): repository 작업 필요
    }

    override suspend fun fetchRecommendTags(
        tag: String,
        type: SearchType,
    ) {
        // TODO(limsaehyun): repository 작업 필요
    }
}
