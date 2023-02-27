/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.viewmodel.state

import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem

/**
 * [isSearchLoading] - 검색 화면의 로딩 상태 유무
 * [recentExam] - 최근 덕질한 시험
 * [searchKeyword] - 검색 키워드
 */
internal data class SearchState(
    val searchStep: SearchStep = SearchStep.Search,
    val isSearchLoading: Boolean = false,
    val recentSearch: ImmutableList<Tag> = persistentListOf(),
    val searchResultTag: Flow<PagingData<Tag>> = flow { PagingData.empty<Tag>() },
    val searchKeyword: String = "",

    val searchTag: String = "",
    val tagSelectedTab: SearchResultStep = SearchResultStep.DuckExam,

    val searchResultForTest: PersistentList<DuckTestCoverItem> = persistentListOf(),
    val searchResultForUser: PersistentList<User> = persistentListOf(),
)
