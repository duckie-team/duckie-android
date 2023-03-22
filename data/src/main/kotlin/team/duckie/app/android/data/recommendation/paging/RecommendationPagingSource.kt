/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress(
    "MaxLineLength",
    "MagicNumber",
    "TooGenericExceptionCaught",
)

package team.duckie.app.android.data.recommendation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.recommendation.repository.RecommendationRepositoryImpl.Companion.RecommendationsPagingPage
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.util.kotlin.ExperimentalApi

private const val STARTING_KEY = 1

internal class RecommendationPagingSource(
    private val fetchRecommendations: suspend (Int) -> RecommendationFeeds,
) : PagingSource<Int, RecommendationItem>() {

    @ExperimentalApi
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendationItem> {
        return try {
            val currentPage = params.key ?: STARTING_KEY
            val response = fetchRecommendations(currentPage)
            LoadResult.Page(
                data = response.recommendations,
                prevKey = if (currentPage == STARTING_KEY) null else currentPage - 1,
                nextKey = if (response.recommendations.isEmpty() || response.recommendations.size < RecommendationsPagingPage) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecommendationItem>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }
}
