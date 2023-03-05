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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.search.mapper.toDomain
import team.duckie.app.android.data.search.model.SearchData
import team.duckie.app.android.data.search.paging.SearchExamPagingSource
import team.duckie.app.android.data.search.paging.SearchTagPagingSource
import team.duckie.app.android.data.search.paging.SearchUserPagingSource
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.search.repository.SearchRepository
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val fuel: Fuel) : SearchRepository {

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
                    searchUsers = {
                        getSearch(
                            query = query,
                            page = it,
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
                    searchExams = {
                        getSearch(
                            query = query,
                            page = it,
                            type = SearchType.EXAMS,
                        ) as Search.ExamSearch
                    },
                )
            },
        ).flow
    }

    override fun getRecentSearch(): ImmutableList<Tag> {
        return persistentListOf() // TODO(limsaehyun): 최근 검색어 가져오기
    }

    override fun clearRecentSearch(tagId: Int) {
        // TODO(limsaehyun): 최근 검색어 지우기
    }

    override fun clearAllRecentSearch() {
        // TODO(limsaehyun): 최근 검색어 지우기
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
                    searchTags = {
                        getSearch(
                            query = query,
                            page = it,
                            type = SearchType.TAGS,
                        ) as Search.TagSearch
                    },
                )
            },
        ).flow
    }

    private suspend fun getSearch(
        query: String,
        page: Int,
        type: SearchType,
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

    internal companion object {
        // 유저 검색 페이징 단위
        const val SearchUserPagingPage = 10

        // 유저 검색 페이징 최대 사이즈
        const val SearchUserPagingMaxSize = 200

        // 유저 검색 페이징 단위
        const val SearchExamPagingPage = 10

        // 유저 검색 페이징 최대 사이즈
        const val SearchExamPagingMaxSize = 200

        /** 유저 검색 페이징 단위 */
        const val SearchTagPagingPage = 10

        // 유저 검색 페이징 최대 사이즈
        const val SearchTagPagingMaxSize = 200
    }
}
