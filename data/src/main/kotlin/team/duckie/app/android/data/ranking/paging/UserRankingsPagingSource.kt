/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("TooGenericExceptionCaught")

package team.duckie.app.android.data.ranking.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.ranking.repository.RankingRepositoryImpl.Companion.MaxSize
import team.duckie.app.android.domain.user.model.User

private const val StartingKey = 1

class UserRankingsPagingSource(
    private val getUserRankings: suspend (Int) -> List<User>,
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: StartingKey
            val response = getUserRankings(currentPage)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == StartingKey) null else currentPage - 1,
                nextKey = if (response.isEmpty() || response.size < MaxSize) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }
}
