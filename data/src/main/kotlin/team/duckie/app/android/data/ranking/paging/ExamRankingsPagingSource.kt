/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.ranking.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.ranking.repository.RankingRepositoryImpl
import team.duckie.app.android.domain.exam.model.Exam

private const val StartingKey = 1

class ExamRankingsPagingSource(
    private val getExamRankings: suspend (Int) -> List<Exam>,
) : PagingSource<Int, Exam>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exam> {
        return try {
            val currentPage = params.key ?: StartingKey
            val response = getExamRankings(currentPage)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == StartingKey) null else currentPage - 1,
                nextKey = if (response.isEmpty() || response.size < RankingRepositoryImpl.MaxSize) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Exam>): Int? {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }
}
