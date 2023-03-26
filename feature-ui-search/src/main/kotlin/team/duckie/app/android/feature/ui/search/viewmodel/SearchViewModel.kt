/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.search.usecase.ClearAllRecentSearchUseCase
import team.duckie.app.android.domain.search.usecase.ClearRecentSearchUseCase
import team.duckie.app.android.domain.search.usecase.GetRecentSearchUseCase
import team.duckie.app.android.domain.search.usecase.PostRecentSearchUseCase
import team.duckie.app.android.domain.search.usecase.SearchExamsUseCase
import team.duckie.app.android.domain.search.usecase.SearchUsersUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchSideEffect
import team.duckie.app.android.feature.ui.search.viewmodel.state.SearchState
import team.duckie.app.android.util.ui.const.Debounce
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchExamsUseCase: SearchExamsUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    private val postRecentSearchUseCase: PostRecentSearchUseCase,
    private val clearAllRecentSearchUseCase: ClearAllRecentSearchUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val followUseCase: FollowUseCase,
    private val getMeUseCase: GetMeUseCase,
) : ContainerHost<SearchState, SearchSideEffect>, ViewModel() {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    private val _searchExams = MutableStateFlow<PagingData<Exam>>(PagingData.empty())
    val searchExams: Flow<PagingData<Exam>> = _searchExams

    private val _searchUsers =
        MutableStateFlow<PagingData<SearchState.SearchUser>>(PagingData.empty())
    val searchUsers: Flow<PagingData<SearchState.SearchUser>> = _searchUsers

    init {
        initState()
    }

    /** [SearchViewModel]의 초기 상태를 설정한다. */
    private fun initState() = intent {
        getMeUseCase().onSuccess { me ->
            reduce { state.copy(me = me) }
        }.onFailure {
            postSideEffect(SearchSideEffect.ReportError(it))
        }
    }

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
            this@apply.debounce(Debounce.SearchSecond).collectLatest { query ->
                refreshSearchStep(keyword = state.searchKeyword)
                // TODO(limsaehyun): 추후 추천 검색어 비즈니스 로직을 이곳에서 작업해야 함
            }
        }
    }

    /** 최근 검색어를 생성한다. */
    private fun postRecentSearch(keyword: String) = intent {
        postRecentSearchUseCase(keyword)
            .onFailure { exception ->
                postSideEffect(SearchSideEffect.ReportError(exception))
            }
    }

    /** 최근 검색어를 가져온다. */
    fun getRecentSearch() = intent {
        getRecentSearchUseCase()
            .onSuccess { tags ->
                reduce {
                    state.copy(
                        recentSearch = tags,
                    )
                }
            }
            .onFailure { exception ->
                postSideEffect(SearchSideEffect.ReportError(exception))
            }
    }

    /** 최근 검색어를 모두 삭제한다. */
    fun clearAllRecentSearch() = intent {
        clearAllRecentSearchUseCase()
            .onSuccess {
                reduce {
                    state.copy(
                        recentSearch = persistentListOf(),
                    )
                }
            }
    }

    /** [tagId]를 가진 최근 검색어를 삭제한다. */
    fun clearRecentSearch(keyword: String) = intent {
        clearRecentSearchUseCase(keyword = keyword)
            .onSuccess {
                reduce {
                    state.copy(
                        recentSearch = state.recentSearch
                            .filter { it != keyword }
                            .toImmutableList(),
                    )
                }
            }
    }

    /** [keyword]에 따른 덕질고사 검색 결과를 가져온다. */
    private fun fetchSearchExams(keyword: String) {
        viewModelScope.launch {
            searchExamsUseCase(exam = keyword)
                .cachedIn(viewModelScope)
                .collect { pagingExam ->
                    _searchExams.value = pagingExam
                }
        }
    }

    /** [keyword]에 따른 유저 검색 결과를 가져온다. */
    private fun fetchSearchUsers(keyword: String) {
        viewModelScope.launch {
            searchUsersUseCase(user = keyword)
                .cachedIn(viewModelScope)
                .map { paging -> paging.map(User::toUiModel) }
                .collect { pagingUser ->
                    _searchUsers.value = pagingUser
                }
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

    /** 검색 키워드가 없다면 검색 화면으로 이동하고, 검색 키워드가 있다면 검색 결과로 이동한다. */
    private fun refreshSearchStep(keyword: String) = intent {
        if (keyword.isEmpty()) {
            navigateSearchStep(
                step = SearchStep.Search,
                keyword = state.searchKeyword,
            )
        } else {
            navigateSearchStep(
                step = SearchStep.SearchResult,
                keyword = state.searchKeyword,
            )
        }
    }

    /** 검색 페이지를 업데이트 한다. */
    private fun navigateSearchStep(
        step: SearchStep,
        keyword: String,
    ) = intent {
        when (step) {
            SearchStep.Search -> {
                getRecentSearch()
            }

            SearchStep.SearchResult -> {
                fetchSearchExams(keyword = keyword)
                fetchSearchUsers(keyword = keyword)
                postRecentSearch(keyword = keyword)
                if (state.searchStep == SearchStep.Search) {
                    postSideEffect(SearchSideEffect.HideKeyBoard)
                }
            }
        }
        reduce {
            state.copy(searchStep = step)
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

    /** [userId]를 팔로우합니다. 이미 팔로우가 되어있다면 언팔로우를 진행합니다. */
    fun followUser(userId: Int, isFollowing: Boolean) = intent {
        followUseCase(
            followBody = FollowBody(
                followingId = userId,
            ),
            isFollowing = isFollowing,
        ).onSuccess {
            _searchUsers.value = _searchUsers.value.map { user ->
                if (user.userId == userId) {
                    user.copy(isFollowing = isFollowing)
                } else {
                    user
                }
            }
        }.onFailure { exception ->
            postSideEffect(SearchSideEffect.ReportError(exception))
        }
    }
}

internal fun User.toUiModel() =
    SearchState.SearchUser(
        userId = id,
        nickname = nickname,
        profileImgUrl = profileImageUrl ?: "",
        isFollowing = follow != null,
        favoriteTag = duckPower?.tag?.name ?: "",
        tier = duckPower?.tier ?: "",
    )
