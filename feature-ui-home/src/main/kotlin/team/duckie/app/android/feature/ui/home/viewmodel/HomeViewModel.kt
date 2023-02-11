/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength") // TODO(limsaehyun): 더미데이터를 위해 임시로 구현, 추후에 제거 필요

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
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.domain.recommendation.usecase.FetchExamMeFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchJumbotronsUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.feature.datastore.me
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.mapper.toUiModel
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.kotlin.fastMap

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchRecommendationsUseCase: FetchRecommendationsUseCase,
    private val fetchJumbotronsUseCase: FetchJumbotronsUseCase,
    private val fetchExamMeFollowingUseCase: FetchExamMeFollowingUseCase,
    private val fetchUserFollowingUseCase: FetchUserFollowingUseCase,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState(me))
    internal val pagingDataFlow: Flow<PagingData<RecommendationItem>>

    init {
        pagingDataFlow = fetchRecommendations()
    }

    fun fetchRecommendations() =
        fetchRecommendationsUseCase()
            .cachedIn(viewModelScope)

    // TODO(limsaehyun: Request Server
    fun fetchJumbotrons() = intent {
        updateHomeLoading(true)
        // TODO(riflockle7): GET /recommendations API commit (params)
        fetchJumbotronsUseCase()
            .onSuccess { jumbotrons ->
                reduce {
                    state.copy(
                        jumbotrons = jumbotrons
                            .fastMap(RecommendationJumbotronItem::toUiModel)
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
        // TODO(riflockle7): GET /exams/me/following API commit (params)
        fetchExamMeFollowingUseCase()
            .onSuccess { exams ->
                reduce {
                    state.copy(
                        recommendFollowingTest = exams
                            .fastMap(Exam::toUiModel)
                            .toPersistentList(),
                    )
                }
            }.onFailure { exception ->
                postSideEffect(HomeSideEffect.ReportError(exception))
            }.also {
                updateHomeLoading(false)
            }
    }

    fun fetchRecommendFollowing() = intent {
        updateHomeLoading(true)
        // TODO(riflockle7): GET /users/following API commit (params)
        fetchUserFollowingUseCase(state.me.id)
            .onSuccess { userFollowing ->
                reduce {
                    state.copy(
                        recommendFollowing = userFollowing.followingRecommendations.fastMap(
                            UserFollowing::toUiModel,
                        ).toPersistentList(),
                    )
                }
            }.onFailure { exception ->
                postSideEffect(HomeSideEffect.ReportError(exception))
            }.also {
                updateHomeLoading(false)
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

    fun navigateToSearchResult(
        searchTag: String,
    ) = intent {
        postSideEffect(HomeSideEffect.NavigateToSearchResult(searchTag))
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
            HomeSideEffect.NavigateToCreateProblem(),
        )
    }
}
