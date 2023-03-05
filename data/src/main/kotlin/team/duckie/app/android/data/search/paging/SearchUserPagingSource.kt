/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("TooGenericExceptionCaught")

package team.duckie.app.android.data.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.search.repository.SearchRepositoryImpl.Companion.SearchUserPagingPage
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.user.model.User

private const val SearchUserStartingKey = 1

internal class SearchUserPagingSource(
    private val searchUsers: suspend (Int) -> Search.UserSearch,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: SearchUserStartingKey
            val response = searchUsers(currentPage)
            LoadResult.Page(
                data = response.users,
                prevKey = if (currentPage == SearchUserStartingKey) null else currentPage - 1,
                nextKey = if (response.users.isEmpty() || response.users.size < SearchUserPagingPage) null else currentPage + 1,
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
