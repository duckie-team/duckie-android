/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.duckie.app.android.data.search.repository.SearchRepositoryImpl.Companion.SearchTagPagingPage
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.tag.model.Tag

private const val SearchTagStartingKey = 1

internal class SearchTagPagingSource(
    private val searchTags: suspend (Int) -> Search.TagSearch,
) : PagingSource<Int, Tag>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tag> {
        return try {
            val currentPage = params.key ?: SearchTagPagingPage
            val response = searchTags(currentPage)
            LoadResult.Page(
                data = response.tags,
                prevKey = if (currentPage == SearchTagStartingKey) null else currentPage - 1,
                nextKey = if (response.tags.isEmpty() || response.tags.size < SearchTagPagingPage) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Tag>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }
}
