/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.kotlin.exception.isFollowingAlreadyExists
import team.duckie.app.android.common.kotlin.exception.isFollowingNotFound
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.usecase.GetExamFundingUseCase
import team.duckie.app.android.domain.exam.usecase.GetExamTagsUseCase
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.home.usecase.GetHomeFundingUseCase
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.usecase.FetchExamMeFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchJumbotronsUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.usecase.FetchRecommendUserFollowingUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.home.constants.HomeStep
import team.duckie.app.android.feature.home.viewmodel.mapper.toFollowingModel
import team.duckie.app.android.feature.home.viewmodel.mapper.toJumbotronModel
import team.duckie.app.android.feature.home.viewmodel.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchRecommendationsUseCase: FetchRecommendationsUseCase,
    private val fetchJumbotronsUseCase: FetchJumbotronsUseCase,
    private val fetchExamMeFollowingUseCase: FetchExamMeFollowingUseCase,
    private val fetchRecommendUserFollowingUseCase: FetchRecommendUserFollowingUseCase,
    private val followUseCase: FollowUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val getHomeFundingUseCase: GetHomeFundingUseCase,
    private val getExamFundingUseCase: GetExamFundingUseCase,
    private val getExamTagsUseCase: GetExamTagsUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    private val _recommendations = MutableStateFlow(PagingData.from(skeletonRecommendationItems))
    internal val recommendations: Flow<PagingData<RecommendationItem>> = _recommendations

    private val _followingExams = MutableStateFlow(PagingData.from(skeletonFollowingExam))
    internal val followingExam: Flow<PagingData<HomeState.RecommendExam>> = _followingExams

    private val pullToRefreshMinLoadingDelay = 1000L

    init {
        fetchJumbotrons()
        fetchRecommendations()
    }

    init {
        initState()
    }

    /** [HomeViewModel]의 초기 상태를 설정한다. */
    private fun initState() = intent {
        getMeUseCase()
            .onSuccess { me ->
                reduce { state.copy(me = me) }
            }.onFailure {
                postSideEffect(HomeSideEffect.ReportError(it))
            }
    }

    /** 추천 피드를 가져온다. */
    private fun fetchRecommendations() {
        viewModelScope.launch {
            fetchRecommendationsUseCase()
                .cachedIn(viewModelScope)
                .collect { recommendations ->
                    _recommendations.value = recommendations
                }
        }
    }

    /**
     * 추천 피드를 새로고침한다.
     * [forceLoading] - PullRefresh 를 할 경우 사용자에게 새로고침이 됐음을 알리기 위한 최소한의 로딩 시간을 부여한다.
     * */
    fun refreshRecommendations(
        forceLoading: Boolean = false,
    ) {
        viewModelScope.launch {
            updateHomeRecommendPullRefreshLoading(true)
            fetchRecommendations()
            if (forceLoading) delay(pullToRefreshMinLoadingDelay)
            updateHomeRecommendPullRefreshLoading(false)
        }
    }

    fun saveJumbotronPage(page: Int) = intent {
        reduce {
            state.copy(jumbotronPage = page)
        }
    }

    /**
     * 팔로잉 추천 탭을 새로고침한다.
     * [forceLoading] - PullRefresh 를 할 경우 사용자에게 새로고침이 됐음을 알리기 위한 최소한의 로딩 시간을 부여한다.
     */
    fun refreshRecommendFollowingExams(
        forceLoading: Boolean = false,
    ) {
        viewModelScope.launch {
            updateHomeRecommendFollowingExamRefreshLoading(true)
            fetchRecommendFollowingExam()
            if (forceLoading) delay(pullToRefreshMinLoadingDelay)
            updateHomeRecommendFollowingExamRefreshLoading(false)
        }
    }

    /**
     * 진행중 탭을 새로고침한다.
     *
     * [forceLoading] - PullRefresh 를 할 경우 사용자에게 새로고침이 됐음을 알리기 위한 최소한의 로딩 시간을 부여한다.
     */
    fun refreshProceedScreen(forceLoading: Boolean = false) {
        viewModelScope.launch {
            updateHomeProceedRefreshLoading(true)
            fetchHomeFundings()
            fetchFundingTags()
            fetchExamFundings(page = 1)
            if (forceLoading) delay(pullToRefreshMinLoadingDelay)
            updateHomeProceedRefreshLoading(false)
        }
    }

    /** 홈 화면의 jumbotron을 가져온다. */
    internal fun fetchJumbotrons() = intent {
        startHomeRecommendLoading()
        fetchJumbotronsUseCase()
            .onSuccess { jumbotrons ->
                reduce {
                    state.copy(
                        isHomeRecommendLoading = false,
                        jumbotrons = jumbotrons
                            .fastMap(Exam::toJumbotronModel)
                            .toImmutableList(),
                    )
                }
            }.onFailure { exception ->
                reduce { state.copy(isHomeRecommendLoading = false, isError = true) }
                postSideEffect(HomeSideEffect.ReportError(exception))
            }
    }

    fun initFollowingExams() {
        updateHomeRecommendFollowingLoading(true)
        fetchRecommendFollowingExam()
        updateHomeRecommendFollowingLoading(false)
    }

    /** 잰행중 탭을 초기화합니다. */
    fun initProceedScreen() {
        updateHomeProceedRefreshLoading(true)
        fetchHomeFundings()
        fetchFundingTags()
        fetchExamFundings(page = 1)
        updateHomeProceedRefreshLoading(false)
    }

    /** 팔로워들의 추천 덕질고사들을 가져온다. */
    private fun fetchRecommendFollowingExam() = intent {
        fetchExamMeFollowingUseCase()
            .cachedIn(viewModelScope)
            .collect { exams ->
                _followingExams.value = exams.map(Exam::toFollowingModel)
            }
    }

    /** 진행중 문제들을 가져온다. */
    private fun fetchHomeFundings() = intent {
        getHomeFundingUseCase().onSuccess {
            reduce { state.copy(homeFundings = it.toImmutableList()) }
        }.onFailure {
            reduce { state.copy(isHomeProceedLoading = false, isError = true) }
            postSideEffect(HomeSideEffect.ReportError(it))
        }
    }

    /** 진행중 화면에서 태그 목록을 가져온다. */
    private fun fetchFundingTags() = intent {
        getExamTagsUseCase().onSuccess {
            val newFundingTags = it.toMutableList().apply { add(0, Tag.all()) }
            reduce { state.copy(homeFundingTags = newFundingTags.toImmutableList()) }
        }.onFailure {
            reduce { state.copy(isHomeProceedLoading = false, isError = true) }
            postSideEffect(HomeSideEffect.ReportError(it))
        }
    }

    /** [tagId] 에 기반하여 펀딩 중인 시험 목록을 가져온다. 전체를 가져오려면 null 을 넣는다. */
    private fun fetchExamFundings(tagId: Int? = null, page: Int, limit: Int = 5) = intent {
        getExamFundingUseCase(tagId, page, limit).onSuccess {
            reduce { state.copy(examFundings = it.toImmutableList()) }
        }.onFailure {
            reduce { state.copy(isHomeProceedLoading = false, isError = true) }
            postSideEffect(HomeSideEffect.ReportError(it))
        }
    }

    /** 팔로잉 탭의 페이징 상태를 관리합니다.  */
    fun handleLoadRecommendFollowingState(loadStates: LoadStates) = intent {
        val errorLoadState = arrayOf(
            loadStates.append,
            loadStates.prepend,
            loadStates.refresh,
        ).filterIsInstance(LoadState.Error::class.java).firstOrNull()

        val exception = errorLoadState?.error

        if (exception != null) {
            if (exception.isFollowingNotFound) {
                reduce {
                    state.copy(
                        isFollowingExist = false,
                    )
                }
            } else {
                postSideEffect(HomeSideEffect.ReportError(exception))
            }
        }
    }

    /** 추천 팔로워들을 가져온다. */
    fun fetchRecommendFollowing() = intent {
        fetchRecommendUserFollowingUseCase(requireNotNull(state.me?.id))
            .onSuccess { userFollowing ->
                reduce {
                    state.copy(
                        recommendFollowing = userFollowing.followingRecommendations.fastMap(
                            UserFollowing::toUiModel,
                        ).toPersistentList(),
                    )
                }
            }.onFailure { exception ->
                if (exception.isFollowingAlreadyExists) {
                    reduce {
                        state.copy(
                            isFollowingExist = true,
                        )
                    }
                    return@onFailure
                }
                postSideEffect(HomeSideEffect.ReportError(exception))
            }
    }

    /** [userId]를 팔로우한다. 이미 팔로우가 되어있다면 언팔로우를 진행한다. */
    fun followUser(userId: Int, isFollowing: Boolean) = intent {
        followUseCase(
            followBody = FollowBody(
                followingId = userId,
            ),
            isFollowing = isFollowing,
        ).onSuccess {
            reduce {
                state.copy(
                    recommendFollowing = state.recommendFollowing.map { recommend ->
                        recommend.copy(
                            users = recommend.users.map { user ->
                                if (user.userId == userId) {
                                    user.copy(
                                        isFollowing = isFollowing,
                                    )
                                } else {
                                    user
                                }
                            }.toImmutableList(),
                        )
                    }.toImmutableList(),
                )
            }
        }.onFailure { exception ->
            postSideEffect(HomeSideEffect.ReportError(exception))
        }
    }

    /** 홈 화면의 단계를 [step]으로 바꾼다. */
    fun changedHomeScreen(
        step: HomeStep,
    ) = intent {
        reduce {
            state.copy(
                homeSelectedIndex = step,
            )
        }
    }

    /** 진행중 화면에서 특정 태그[tag]를 클릭합니다. */
    fun clickProceedFundingTag(tag: Tag) = intent {
        reduce { state.copy(homeFundingSelectedTag = tag) }
        val tagId = if (tag == Tag.all()) null else tag.id
        fetchExamFundings(tagId, 1, 5)
    }

    /** 홈 화면의 추천 탭의 로딩 상태를 [loading]으로 바꾼다. */
    private fun startHomeRecommendLoading() = intent {
        reduce {
            state.copy(isHomeRecommendLoading = true, isError = false)
        }
    }

    /** 홈 화면의 팔로잉 탭의 로딩 상태를 [loading]으로 바꾼다. */
    private fun updateHomeRecommendFollowingLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(isHomeRecommendFollowingExamLoading = loading, isError = false)
        }
    }

    /** 홈 화면의 추천 탭의 pull refresh 로딩 상태를 [loading]으로 바꾼다. */
    private fun updateHomeRecommendPullRefreshLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeRecommendPullRefreshLoading = loading,
                isHomeRecommendLoading = loading, // for skeleton UI
                isError = false,
            )
        }
    }

    /** 홈 화면의 팔로잉 추천 탭의 pull refresh 로딩 상태를 [loading]으로 바꾼다. */
    private fun updateHomeRecommendFollowingExamRefreshLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeRecommendFollowingExamRefreshLoading = loading,
                isHomeRecommendFollowingExamLoading = loading, // for skeleton UI
                isError = false,
            )
        }
    }

    /** 홈 화면의 진행중 탭의 pull refresh 로딩 상태를 [loading]으로 바꾼다. */
    private fun updateHomeProceedRefreshLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeProceedPullRefreshLoading = loading,
                isHomeProceedLoading = loading, // for skeleton UI
                isError = false,
            )
        }
    }
}
