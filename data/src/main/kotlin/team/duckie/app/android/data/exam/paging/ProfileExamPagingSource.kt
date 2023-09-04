/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:Suppress("TooGenericExceptionCaught")

package team.duckie.app.android.data.exam.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl
import team.duckie.app.android.domain.exam.model.ProfileExam

private const val STARTING_KEY = 1

class ProfileExamPagingSource(
    private val getProfileExam: suspend (Int) -> List<ProfileExam>,
) : PagingSource<Int, ProfileExam>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileExam> {
        return try {
            val currentPage = params.key ?: STARTING_KEY
            val response = getProfileExam(currentPage)

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == STARTING_KEY) null else currentPage - 1,
                nextKey = if (response.isEmpty() || response.size < ExamRepositoryImpl.ExamMePagingPage) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProfileExam>): Int? {
        return ((state.anchorPosition ?: STARTING_KEY) - state.config.initialLoadSize / 2)
            .coerceAtLeast(STARTING_KEY)
    }
}
