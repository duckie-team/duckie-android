/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.home

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
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.usecase.FetchExamMeFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchJumbotronsUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase
import team.duckie.app.android.domain.tag.usecase.FetchPopularTagsUseCase
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.usecase.FetchRecommendUserFollowingUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toFollowingModel
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toJumbotronModel
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toUiModel
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.app.android.util.kotlin.exception.isFollowingAlreadyExists
import team.duckie.app.android.util.kotlin.exception.isFollowingNotFound
import team.duckie.app.android.util.kotlin.fastMap
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchRecommendationsUseCase: FetchRecommendationsUseCase,
    private val fetchJumbotronsUseCase: FetchJumbotronsUseCase,
    private val fetchExamMeFollowingUseCase: FetchExamMeFollowingUseCase,
    private val fetchRecommendUserFollowingUseCase: FetchRecommendUserFollowingUseCase,
    private val followUseCase: FollowUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val fetchPopularTagsUseCase: FetchPopularTagsUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    private val _recommendations = MutableStateFlow(PagingData.from(skeletonRecommendationItems))
    internal val recommendations: Flow<PagingData<RecommendationItem>> = _recommendations

    private val _followingExams = MutableStateFlow(PagingData.from(skeletonFollowingExam))
    internal val followingExam: Flow<PagingData<HomeState.RecommendExam>> = _followingExams

    private val pullToRefreshMinLoadingDelay = 1000L

    init {
        initState()
        fetchJumbotrons()
        fetchRecommendations()
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

    /** 홈 화면의 jumbotron을 가져온다. */
    private fun fetchJumbotrons() = intent {
        updateHomeRecommendLoading(true)
        fetchJumbotronsUseCase()
            .onSuccess { jumbotrons ->
                reduce {
                    state.copy(
                        jumbotrons = jumbotrons
                            .fastMap(Exam::toJumbotronModel)
                            .toPersistentList(),
                    )
                }
            }.onFailure { exception ->
                postSideEffect(HomeSideEffect.ReportError(exception))
            }.also {
                updateHomeRecommendLoading(false)
            }
    }

    fun initFollowingExams() {
        updateHomeRecommendFollowingLoading(true)
        fetchRecommendFollowingExam()
        updateHomeRecommendFollowingLoading(false)
    }

    /** 팔로워들의 추천 덕질고사들을 가져온다. */
    private fun fetchRecommendFollowingExam() = intent {
        fetchExamMeFollowingUseCase()
            .cachedIn(viewModelScope)
            .collect { exams ->
                _followingExams.value = exams.map(Exam::toFollowingModel)
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

    /** [userId]를 팔로우합니다. 이미 팔로우가 되어있다면 언팔로우를 진행합니다. */
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

    /** 인기 태그 목록을 가져옵니다. */
    fun fetchPopularTags() = intent {
        fetchPopularTagsUseCase()
            .onSuccess { tags ->
                reduce {
                    state.copy(
                        popularTags = tags,
                    )
                }
            }
            .onFailure { exception ->
                postSideEffect(HomeSideEffect.ReportError(exception))
            }
    }

    /** 홈 화면의 추천 탭의 로딩 상태를 [loading]으로 바꿉니다. */
    private fun updateHomeRecommendLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(isHomeRecommendLoading = loading)
        }
    }

    /** 홈 화면의 팔로잉 탭의 로딩 상태를 [loading]으로 바꿉니다. */
    private fun updateHomeRecommendFollowingLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(isHomeRecommendFollowingExamLoading = loading)
        }
    }

    private fun updateHomeRecommendPullRefreshLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeRecommendPullRefreshLoading = loading,
                isHomeRecommendLoading = loading, // 스캘레톤 UI를 위해 업데이트
            )
        }
    }

    private fun updateHomeRecommendFollowingExamRefreshLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeRecommendFollowingExamRefreshLoading = loading,
                isHomeRecommendFollowingExamLoading = loading, // 스캘레톤 UI를 위해 업데이트)
            )
        }
    }

    /** 홈 화면의 BottomNavigation 상태를 [step]으로 바꿉니다. */
    fun navigationPage(
        step: BottomNavigationStep,
    ) = intent {
        if (step == BottomNavigationStep.RankingScreen && state.bottomNavigationStep == BottomNavigationStep.RankingScreen) {
            postSideEffect(HomeSideEffect.ClickRankingRetry)
        }
        reduce {
            state.copy(
                bottomNavigationStep = step,
            )
        }
    }

    /** 홈 화면의 단계를 [step]으로 바꿉니다. */
    fun changedHomeScreen(
        step: HomeStep,
    ) = intent {
        reduce {
            state.copy(
                homeSelectedIndex = step,
            )
        }
    }

    /** 검색 화면으로 이동한다. */
    fun navigateToSearch(
        searchTag: String? = null,
    ) = intent {
        postSideEffect(HomeSideEffect.NavigateToSearch(searchTag))
    }

    /** 홈 디테일 화면으로 이동한다. */
    fun navigateToHomeDetail(
        examId: Int,
    ) = intent {
        postSideEffect(
            HomeSideEffect.NavigateToHomeDetail(
                examId = examId,
            ),
        )
    }

    /** 문제 만들기 화면으로 이동한다 */
    fun navigateToCreateProblem() = intent {
        postSideEffect(HomeSideEffect.NavigateToCreateProblem)
    }

    fun navigateFriends(friendType: FriendsType) = intent {
        postSideEffect(HomeSideEffect.NavigateToFriends(friendType, state.me?.id ?: 0))
    }

    /** 설정 화면으로 이동한다 */
    fun navigateToSetting() = intent {
        postSideEffect(HomeSideEffect.NavigateToSetting)
    }

    /** 알림 화면으로 이동한다 */
    fun navigateToNotification() = intent {
        postSideEffect(HomeSideEffect.NavigateToNotification)
    }

    fun updateGuideVisible(visible: Boolean) = intent {
        reduce { state.copy(guideVisible = visible) }
    }
}
