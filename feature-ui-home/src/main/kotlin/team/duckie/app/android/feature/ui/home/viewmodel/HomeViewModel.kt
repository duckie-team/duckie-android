/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.usecase.GetRecentExamUseCase
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.usecase.FetchExamMeFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchJumbotronsUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase
import team.duckie.app.android.domain.tag.usecase.FetchPopularTagsUseCase
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toFollowingModel
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toJumbotronModel
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toUiModel
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.exception.handling.const.ErrorCode
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.exception.DuckieResponseException
import team.duckie.app.android.util.kotlin.fastMap

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchRecommendationsUseCase: FetchRecommendationsUseCase,
    private val fetchJumbotronsUseCase: FetchJumbotronsUseCase,
    private val fetchExamMeFollowingUseCase: FetchExamMeFollowingUseCase,
    private val fetchUserFollowingUseCase: FetchUserFollowingUseCase,
    private val followUseCase: FollowUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val fetchPopularTagsUseCase: FetchPopularTagsUseCase,
    private val getRecentExamUseCase: GetRecentExamUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    private val _recommendations =
        MutableStateFlow<PagingData<RecommendationItem>>(PagingData.empty())
    internal val recommendations: Flow<PagingData<RecommendationItem>> = _recommendations

    init {
        initState()
        fetchJumbotrons()
        fetchRecommendations()
    }

    /** [HomeViewModel]의 초기 상태를 설정한다. */
    private fun initState() = intent {
        getMeUseCase().onSuccess { me ->
            if (me != null) {
                reduce { state.copy(me = me) }
            } else {
                postSideEffect(HomeSideEffect.ReportError(Throwable("me is null")))
            }
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

    /** 홈 화면의 jumbotron을 가져온다. */
    private fun fetchJumbotrons() = intent {
        updateHomeLoading(true)
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
                updateHomeLoading(false)
            }
    }

    /** 최근 덕질한 시험 목록을 가져온다. */
    fun fetchRecentExam() = intent {
        getRecentExamUseCase()
            .onSuccess { exams ->
                reduce {
                    state.copy(
                        recentExam = exams.toImmutableList(),
                    )
                }
            }
    }

    /** 팔로워들의 추천 덕질고사들을 가져온다. */
    fun fetchRecommendFollowingTest() = intent {
        updateHomeLoading(true)
        fetchExamMeFollowingUseCase()
            .onSuccess { exams ->
                reduce {
                    state.copy(
                        recommendFollowingTest = exams
                            .fastMap(Exam::toFollowingModel)
                            .toPersistentList(),
                    )
                }
            }
            .onFailure { exception ->
                if ((exception as? DuckieResponseException)?.code == ErrorCode.FollowingNotFound) {
                    reduce {
                        state.copy(
                            isFollowingExist = false,
                        )
                    }
                    return@onFailure
                }
                postSideEffect(HomeSideEffect.ReportError(exception))
            }.also {
                updateHomeLoading(false)
            }
    }

    /** 추천 팔로워들을 가져온다. */
    fun fetchRecommendFollowing() = intent {
        updateHomeLoading(true)
        fetchUserFollowingUseCase(requireNotNull(state.me?.id))
            .onSuccess { userFollowing ->
                reduce {
                    state.copy(
                        recommendFollowing = userFollowing.followingRecommendations.fastMap(
                            UserFollowing::toUiModel,
                        ).toPersistentList(),
                    )
                }
            }.onFailure { exception ->
                if ((exception as? DuckieResponseException)?.code == ErrorCode.FollowingAlreadyExists) {
                    reduce {
                        state.copy(
                            isFollowingExist = true,
                        )
                    }
                    return@onFailure
                }
                postSideEffect(HomeSideEffect.ReportError(exception))
            }.also {
                updateHomeLoading(false)
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

    /** 홈 화면의 로딩 상태를 [loading]으로 바꿉니다. */
    private fun updateHomeLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeLoading = loading,
            )
        }
    }

    /** 홈 화면의 BottomNavigation 상태를 [step]으로 바꿉니다. */
    fun navigationPage(
        step: BottomNavigationStep,
    ) = intent {
        reduce {
            state.copy(
                step = step,
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

    /** 문제 만들기 화면으로 이동한다.*/
    fun navigateToCreateProblem() = intent {
        postSideEffect(
            HomeSideEffect.NavigateToCreateProblem,
        )
    }
}
