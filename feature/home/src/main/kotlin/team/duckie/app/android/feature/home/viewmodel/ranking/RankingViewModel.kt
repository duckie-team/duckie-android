/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.fastMapIndexed
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.ranking.usecase.GetExamRankingsByAnswerRate
import team.duckie.app.android.domain.ranking.usecase.GetExamRankingsBySolvedCount
import team.duckie.app.android.domain.ranking.usecase.GetUserRankingsUseCase
import team.duckie.app.android.domain.tag.usecase.FetchPopularTagsUseCase
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

@HiltViewModel
internal class RankingViewModel @Inject constructor(
    private val getUserRankingsUseCase: GetUserRankingsUseCase,
    private val getExamRankingBySolvedCountUseCase: GetExamRankingsBySolvedCount,
    private val getExamRankingByAnswerRateUseCase: GetExamRankingsByAnswerRate,
    private val fetchPopularTagsUseCase: FetchPopularTagsUseCase,
) : ContainerHost<RankingState, RankingSideEffect>, ViewModel() {
    override val container = container<RankingState, RankingSideEffect>(RankingState())

    private val _examRankings = MutableStateFlow(PagingData.from(skeletonExams()))
    val examRankings: Flow<PagingData<Exam>> = _examRankings

    private val _userRankings = MutableStateFlow(PagingData.from(skeletonExamineeItems))
    val userRankings: Flow<PagingData<User>> = _userRankings

    fun refresh() {
        fetchPopularTags()
        getExams()
        getUserRankings()
    }

    private fun getUserRankings() = intent {
        getUserRankingsUseCase()
            .cachedIn(viewModelScope)
            .catch {
                postSideEffect(RankingSideEffect.ReportError(it))
            }
            .collect { pagingUser ->
                _userRankings.value = pagingUser
            }
    }

    private fun getExams() = with(container.stateFlow.value) {
        val tagId = run {
            val index = tagSelections.indexOf(true)
            if (index == 0) {
                null
            } else {
                examTags[index].id
            }
        }
        when (ExamRankingOrder.from(selectedExamOrder)) {
            ExamRankingOrder.AnswerRate -> getExamRankingByAnswerRate(tagId)
            ExamRankingOrder.SolvedCount -> getExamRankingBySolvedCount(tagId)
        }
    }

    private fun getExamRankingByAnswerRate(
        tagId: Int?,
    ) = intent {
        updatePagingDataLoading(true)
        getExamRankingByAnswerRateUseCase(tagId)
            .cachedIn(viewModelScope)
            .catch {
                postSideEffect(RankingSideEffect.ReportError(it))
            }
            .collect { pagingExam ->
                _examRankings.value = pagingExam
                updatePagingDataLoading(false)
            }
    }

    private fun getExamRankingBySolvedCount(
        tagId: Int?,
    ) = intent {
        updatePagingDataLoading(true)
        getExamRankingBySolvedCountUseCase(tagId)
            .cachedIn(viewModelScope)
            .catch {
                postSideEffect(RankingSideEffect.ReportError(it))
            }
            .collect { pagingExam ->
                _examRankings.value = pagingExam
                updatePagingDataLoading(false)
            }
    }

    private fun fetchPopularTags() = intent {
        startTagLoading()
        fetchPopularTagsUseCase()
            .onSuccess { tags ->
                reduce {
                    state.copy(
                        isTagLoading = false,
                        examTags = tags.addAllTag(),
                        tagSelections = tags.fastMap { false }.toImmutableList().addAllSelection(),
                    )
                }
            }
            .onFailure { exception ->
                reduce {
                    state.copy(
                        isTagLoading = false,
                        isError = true,
                    )
                }
                postSideEffect(RankingSideEffect.ReportError(exception))
            }
    }

    fun setSelectedTab(index: Int) = intent {
        reduce {
            state.copy(selectedTab = index)
        }
    }

    fun changeSelectedTags(index: Int) = intent {
        reduce {
            state.copy(
                tagSelections = state.tagSelections
                    .fastMapIndexed { mapIndex, _ -> mapIndex == index }
                    .toImmutableList(),
            )
        }
        getExams()
    }

    fun setSelectedExamOrder(index: Int) = intent {
        reduce {
            state.copy(selectedExamOrder = index)
        }
        getExams()
    }

    @Suppress("NoConsecutiveBlankLines") // 시험 생성하기가 가능한 스펙에서 활용
    fun clickAppBarRightIcon() = intent {
        postSideEffect(RankingSideEffect.NavigateToCreateExam)
    }

    fun clickExam(examId: Int) = intent {
        postSideEffect(RankingSideEffect.NavigateToExamDetail(examId))
    }

    fun clickRetryRanking() = intent {
        postSideEffect(RankingSideEffect.ListPullUp(state.selectedTab))
    }

    fun clickUser(userId: Int) = intent {
        postSideEffect(RankingSideEffect.NavigateToUserProfile(userId))
    }

    private fun updatePagingDataLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isPagingDataLoading = loading,
            )
        }
    }

    private fun startTagLoading() = intent {
        reduce {
            state.copy(
                isTagLoading = true,
                isError = false,
            )
        }
    }
}
