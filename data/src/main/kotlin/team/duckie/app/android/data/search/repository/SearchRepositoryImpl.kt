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
import com.github.kittinunf.fuel.Fuel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.search.mapper.toDomain
import team.duckie.app.android.data.search.model.SearchData
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
    private val fuel: Fuel, // TODO (limsaehyun) DataSource로 분리해야 함
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {

    // TODO(limsaehyun) : 검색의 관심사 분리를 위해 제거해야 함
    override suspend fun getSearch(
        query: String,
        page: Int,
        type: String,
    ): Search = withContext(Dispatchers.IO) {
        val (_, response) = fuel.get(
            "/search",
            listOf(
                "query" to query,
                "page" to page,
                "type" to type,
            ),
        ).responseString()

        return@withContext responseCatchingFuel(
            response,
            SearchData::toDomain,
        )
    }

    override fun searchUsers(
        query: String,
    ): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchUserPagingPage,
                enablePlaceholders = true,
                maxSize = SearchUserPagingMaxSize,
            ),
            pagingSourceFactory = {
                SearchUserPagingSource(
                    searchUsers = { page ->
                        searchRemoteDataSource.getSearch(
                            query = query,
                            page = page,
                            type = SearchType.USERS,
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
                maxSize = SearchExamPagingMaxSize,
            ),
            pagingSourceFactory = {
                SearchExamPagingSource(
                    searchExams = { page ->
                        searchRemoteDataSource.getSearch(
                            query = query,
                            page = page,
                            type = SearchType.EXAMS,
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
                maxSize = SearchTagPagingMaxSize,
            ),
            pagingSourceFactory = {
                SearchTagPagingSource(
                    searchTags = { page ->
                        searchRemoteDataSource.getSearch(
                            query = query,
                            page = page,
                            type = SearchType.TAGS,
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

        /** 유저 검색 페이징 최대 사이즈 */
        const val SearchUserPagingMaxSize = 200

        /** 유저 검색 페이징 단위 */
        const val SearchExamPagingPage = 10

        /** 유저 검색 페이징 최대 사이즈 */
        const val SearchExamPagingMaxSize = 200

        /** 유저 검색 페이징 단위 */
        const val SearchTagPagingPage = 10

        /** 유저 검색 페이징 최대 사이즈 */
        const val SearchTagPagingMaxSize = 200
    }
}
