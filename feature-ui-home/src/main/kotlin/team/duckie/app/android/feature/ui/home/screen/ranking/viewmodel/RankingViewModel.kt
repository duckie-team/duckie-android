/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.ranking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.search.usecase.SearchExamsUseCase
import team.duckie.app.android.domain.tag.usecase.FetchPopularTagsUseCase
import team.duckie.app.android.feature.ui.home.screen.ranking.dummy.skeletonRankingExams
import team.duckie.app.android.feature.ui.home.screen.ranking.sideeffect.RankingSideEffect
import team.duckie.app.android.feature.ui.home.screen.ranking.state.RankingState
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.fastMapIndexed
import javax.inject.Inject

@HiltViewModel
internal class RankingViewModel @Inject constructor(
    private val searchExamsUseCase: SearchExamsUseCase,
    private val fetchPopularTagsUseCase: FetchPopularTagsUseCase,
) : ContainerHost<RankingState, RankingSideEffect>, ViewModel() {
    override val container = container<RankingState, RankingSideEffect>(RankingState())

    private val _searchExams = MutableStateFlow(PagingData.from(skeletonRankingExams))
    val searchExams: Flow<PagingData<Exam>> = _searchExams

    // TODO(EvergreenTree97): 추후 GET /ranking/exams 로 구현
    fun fetchSearchExams(keyword: String) = intent {
        updateLoading(true)
        viewModelScope.launch {
            searchExamsUseCase(exam = keyword)
                .cachedIn(viewModelScope)
                .catch {
                    postSideEffect(RankingSideEffect.ReportError(it))
                }
                .collect { pagingExam ->
                    _searchExams.value = pagingExam
                }
        }
    }

    fun fetchPopularTags() = intent {
        fetchPopularTagsUseCase()
            .onSuccess { tags ->
                reduce {
                    state.copy(
                        examTags = tags,
                    )
                }
            }
            .onFailure { exception ->
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
            with(state) {
                if (index == 0) { // "전체" 태그라면
                    if (tagSelections[index]) {
                        copy(tagSelections = tagSelections.fastMap { false }.toImmutableList())
                    } else {
                        copy(
                            tagSelections = tagSelections
                                .fastMapIndexed { mapIndex, _ -> mapIndex == 0 }
                                .toImmutableList(),
                        )
                    }
                } else {
                    if (tagSelections[0]) {
                        copy(
                            tagSelections = tagSelections.copy {
                                this[0] = false
                                this[index] = this[index].not()
                            },
                        )
                    } else {
                        copy(tagSelections = tagSelections.copy { this[index] = this[index].not() })
                    }
                }
            }
        }
    }

    fun setSelectedExamOrder(index: Int) = intent {
        reduce {
            state.copy(selectedExamOrder = index)
        }
    }

    fun clickAppBarRightIcon() = intent {
        postSideEffect(RankingSideEffect.NavigateToCreateProblem)
    }

    fun clickExam(examId: Int) = intent {
        postSideEffect(RankingSideEffect.NavigateToExamDetail(examId))
    }

    private fun updateLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(
                isLoading = loading,
            )
        }
    }
}
