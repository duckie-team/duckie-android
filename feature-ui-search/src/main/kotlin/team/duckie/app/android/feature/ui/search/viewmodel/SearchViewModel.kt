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
import team.duckie.app.android.domain.search.usecase.SearchUsersUseCase
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchSideEffect
import team.duckie.app.android.feature.ui.search.viewmodel.state.SearchState
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchExamsUseCase: SearchExamsUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
) : ContainerHost<SearchState, SearchSideEffect>, ViewModel() {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    private val searchDebounce: Long = 1500L

    /**
     * 추천 검색어 flow. [searchDebounce] 시간에 따라 추천 검색어를 가져온다.
     * 현재는 추천 검색어 기능을 지원하지 않아 검색어 입력 시 [refreshSearchStep]를 실행시킨다.
     **/
    private val _getRecommendKeywords: MutableSharedFlow<String> = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply {
        intent {
            this@apply.debounce(searchDebounce).collectLatest { query ->
                refreshSearchStep(keyword = state.searchKeyword)
                // TODO(limsaehyun): 추후 추천 검색어 비즈니스 로직을 이곳에서 작업해야 함
            }
        }
    }

    fun clearAllRecentSearch() {
        // TODO(limsaehyun): 최근 검색어 모두 삭제 구현
    }

    fun clearRecentSearch(tagId: Int) {
        // TODO(limsaehyun): 최근 검색어 삭제 구현
    }

    fun fetchSearchExams(keyword: String) = intent {
        searchExamsUseCase(exam = keyword)
            .onSuccess { paging ->
                reduce {
                    state.copy(
                        searchExams = paging,
                    )
                }
            }
            .onFailure { exception ->
                postSideEffect(SearchSideEffect.ReportError(exception))
            }
    }

    fun fetchSearchUsers(user: String) = intent {
        searchUsersUseCase(user = user)
            .onSuccess { paging ->
                reduce {
                    state.copy(
                        searchUsers = paging,
                    )
                }
            }
            .onFailure { exception ->
                postSideEffect(SearchSideEffect.ReportError(exception))
            }
    }

    /** 검색 화면에서 [query] 값에 맞는 검색 결과를 가져온다. */
    private suspend fun recommendKeywords(query: String) {
        _getRecommendKeywords.emit(query)
    }

    /** 검색 키워드를 업데이트한다. 업데이트 후 [recommendKeywords]를 호출한다. */
    fun updateSearchKeyword(
        keyword: String,
        debounce: Boolean = true,
    ) = intent {
        reduce {
            state.copy(searchKeyword = keyword)
        }.run {
            recommendKeywords(query = keyword)
            if (!debounce) refreshSearchStep(keyword = keyword)
        }
    }

    /** 검색 페이지를 업데이트한다. 검색 키워드가 있다면 검색 결과로 이동한다. */
    private fun refreshSearchStep(keyword: String) = intent {
        if (keyword.isEmpty()) {
            reduce {
                state.copy(searchStep = SearchStep.Search)
            }
        } else {
            reduce {
                state.copy(searchStep = SearchStep.SearchResult)
            }
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
