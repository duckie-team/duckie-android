/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.search.repository.SearchRepositoryImpl.Companion.SearchExamPagingPage
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.search.model.Search

private const val SearchExamStartingKey = 1

internal class SearchExamPagingSource(
    private val searchExams: suspend (Int) -> Search.ExamSearch,
) : PagingSource<Int, Exam>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exam> {
        return try {
            val currentPage = params.key ?: SearchExamStartingKey
            val response = searchExams(currentPage)
            LoadResult.Page(
                data = response.exams,
                prevKey = if (currentPage == SearchExamStartingKey) null else currentPage - 1,
                nextKey = if (response.exams.isEmpty() || response.exams.size < SearchExamPagingPage) null else currentPage + 1,
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
