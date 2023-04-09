/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.data.search.paging.SearchExamPagingSource
import team.duckie.app.android.data.search.paging.SearchTagPagingSource
import team.duckie.app.android.data.search.paging.SearchUserPagingSource
import team.duckie.app.android.data.search.datasource.SearchLocalDataSource
import team.duckie.app.android.data.search.datasource.SearchRemoteDataSource
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.search.repository.SearchRepository
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {

    override suspend fun getSearch(
        query: String,
        page: Int,
        type: SearchType,
    ): Search {
        return searchRemoteDataSource.getSearch(
            query = query,
            page = page,
            type = type,
        )
    }

    override fun searchUsers(
        query: String,
    ): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchUserPagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                SearchUserPagingSource(
                    searchUsers = { page ->
                        searchRemoteDataSource.getSearch(
                            query = query,
                            page = page,
                            type = SearchType.Users,
                        ) as Search.UserSearch
                    },
                )
            },
        ).flow
    }

    override fun searchExams(
        query: String,
    ): Flow<PagingData<Exam>> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchExamPagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                SearchExamPagingSource(
                    searchExams = { page ->
                        searchRemoteDataSource.getSearch(
                            query = query,
                            page = page,
                            type = SearchType.Exams,
                        ) as Search.ExamSearch
                    },
                )
            },
        ).flow
    }

    override suspend fun clearRecentSearch(keyword: String) {
        searchLocalDataSource.clearRecentSearch(keyword = keyword)
    }

    override suspend fun clearAllRecentSearch() {
        searchLocalDataSource.clearAllRecentSearch()
    }

    override fun searchTags(
        query: String,
    ): Flow<PagingData<Tag>> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchTagPagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                SearchTagPagingSource(
                    searchTags = { page ->
                        searchRemoteDataSource.getSearch(
                            query = query,
                            page = page,
                            type = SearchType.Tags,
                        ) as Search.TagSearch
                    },
                )
            },
        ).flow
    }

    override suspend fun saveRecentSearch(keyword: String) {
        searchLocalDataSource.saveRecentSearch(keyword = keyword)
    }

    override suspend fun getRecentSearch(): ImmutableList<String> {
        return searchLocalDataSource.getRecentSearch()
    }

    internal companion object {
        /** 유저 검색 페이징 단위 */
        const val SearchUserPagingPage = 10

        /** 유저 검색 페이징 단위 */
        const val SearchExamPagingPage = 10

        /** 유저 검색 페이징 단위 */
        const val SearchTagPagingPage = 10
    }
}
