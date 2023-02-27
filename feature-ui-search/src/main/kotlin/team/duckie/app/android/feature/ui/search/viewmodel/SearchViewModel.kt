/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.search.usecase.SearchExamsUseCase
import team.duckie.app.android.domain.search.usecase.SearchTagsUseCase
import team.duckie.app.android.domain.search.usecase.SearchUsersUseCase
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchSideEffect
import team.duckie.app.android.feature.ui.search.viewmodel.state.SearchState
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchTagsUseCase: SearchTagsUseCase,
    private val searchExamsUseCase: SearchExamsUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
) : ContainerHost<SearchState, SearchSideEffect>, ViewModel() {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    private val searchDebounce: Long = 1500L

    /** 검색 flow. [searchDebounce] 시간에 따라 검색 결과를 페이징을 적용하여 가져온다. */
    private val _getSearchTags: MutableSharedFlow<String> = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply {
        intent {
            this@apply.debounce(searchDebounce).collectLatest { query ->
                searchTagsUseCase(tag = query)
                    .onSuccess {
                        reduce {
                            state.copy(
                                searchResultTag = it,
                            )
                        }
                    }
                    .onFailure {
                    }
            }
        }
    }

    // TODO(limsaehyun): Request Server
    fun fetchSearchResultForExam(tag: String) = intent {
    }

    // TODO(limsaehyun): Request Server
    fun fetchSearchResultForUser(user: String) = intent {
    }

    /** 검색 화면에서 [query] 값에 맞는 검색 결과를 가져온다. */
    private suspend fun searchTags(query: String) {
        _getSearchTags.emit(query)
    }

    /** 검색 키워드를 업데이트한다. 업데이트 후 [searchTags]를 호출한다. */
    fun updateSearchKeyword(keyword: String) = intent {
        reduce {
            state.copy(searchKeyword = keyword)
        }.run {
            searchTags(query = keyword)
        }
    }

    /** 검색 화면의 로딩 상태를 업데이트한다.*/
    private fun updateSearchLoadingState(loading: Boolean) = intent {
        reduce {
            state.copy(
                isSearchLoading = loading,
            )
        }
    }

    /** 검색 화면의 단계를 업데이트한다. */
    fun updateSearchResultTab(
        step: SearchResultStep,
    ) = intent {
        reduce {
            state.copy(
                tagSelectedTab = step,
            )
        }
    }
}
