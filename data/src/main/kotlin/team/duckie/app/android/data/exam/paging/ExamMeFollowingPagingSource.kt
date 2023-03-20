/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl.Companion.ExamMePagingPage
import team.duckie.app.android.domain.exam.model.Exam

private const val STARTING_KEY = 1

internal class ExamMeFollowingPagingSource(
    private val getExamMeFollowing: suspend (Int) -> List<Exam>,
) : PagingSource<Int, Exam>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exam> {
        return try {
            val currentPage = params.key ?: STARTING_KEY
            val response = getExamMeFollowing(currentPage)

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == STARTING_KEY) null else currentPage - 1,
                nextKey = if (response.isEmpty() || response.size < ExamMePagingPage) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Exam>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }
}
