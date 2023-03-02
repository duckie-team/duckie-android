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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
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
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())
    internal val pagingDataFlow: Flow<PagingData<RecommendationItem>>

    init {
        initState()
        fetchJumbotrons()
        pagingDataFlow = fetchRecommendations()
    }

    fun initState() = intent {
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

    private fun fetchRecommendations() =
        fetchRecommendationsUseCase()
            .cachedIn(viewModelScope)

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
                if ((exception as? DuckieResponseException)?.code == "FOLLOWING_NOT_FOUND") {
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
                if ((exception as? DuckieResponseException)?.code == "FOLLOWING_ALREADY_EXISTS") {
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

    fun followUser(userId: Int, isFollowing: Boolean) = intent {
        followUseCase(
            followBody = FollowBody(
                followingId = userId,
            ),
            isFollowing = isFollowing,
        ).onFailure { exception ->
            postSideEffect(HomeSideEffect.ReportError(exception))
        }
    }

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

    private fun updateHomeLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isHomeLoading = loading,
            )
        }
    }

    fun navigationPage(
        step: BottomNavigationStep,
    ) = intent {
        reduce {
            state.copy(
                step = step,
            )
        }
    }

    fun changedHomeScreen(
        step: HomeStep,
    ) = intent {
        reduce {
            state.copy(
                homeSelectedIndex = step,
            )
        }
    }

    fun navigateToSearch(
        searchTag: String? = null,
    ) = intent {
        postSideEffect(HomeSideEffect.NavigateToSearch(searchTag))
    }

    fun navigateToHomeDetail(
        examId: Int,
    ) = intent {
        postSideEffect(
            HomeSideEffect.NavigateToHomeDetail(
                examId = examId,
            ),
        )
    }

    fun navigateToCreateProblem() = intent {
        postSideEffect(
            HomeSideEffect.NavigateToCreateProblem,
        )
    }
}
