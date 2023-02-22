/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceUseCase
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.heart.usecase.DeleteHeartUseCase
import team.duckie.app.android.domain.heart.usecase.PostHeartUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.util.kotlin.DuckieResponseFieldNPE
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val getMeUseCase: GetMeUseCase,
    private val followUseCase: FollowUseCase,
    private val postHeartUseCase: PostHeartUseCase,
    private val deleteHeartUseCase: DeleteHeartUseCase,
    private val makeExamInstanceUseCase: MakeExamInstanceUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<DetailState, DetailSideEffect>, ViewModel() {
    override val container = container<DetailState, DetailSideEffect>(DetailState.Loading)

    suspend fun initState() {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value

        val exam = runCatching { examRepository.getExam(examId) }.getOrNull()
        intent {
            getMeUseCase()
                .onSuccess { me ->
                    reduce {
                        if (exam != null && me != null) {
                            DetailState.Success(exam, me, exam.user?.follow != null)
                        } else {
                            DetailState.Error(DuckieResponseFieldNPE("exam or me is Null"))
                        }
                    }
                }.onFailure {
                    postSideEffect(DetailSideEffect.ReportError(it))
                }
        }
    }

    suspend fun refresh() {
        initState()
    }

    fun followUser() = viewModelScope.launch {
        val detailState = container.stateFlow.value
        require(detailState is DetailState.Success)

        followUseCase(
            FollowBody(
                detailState.exam.user?.id ?: duckieResponseFieldNpe("팔로우할 유저는 반드시 있어야 합니다."),
            ),
            !detailState.isFollowing,
        ).onSuccess { apiResult ->
            if (apiResult) {
                intent {
                    reduce {
                        (state as DetailState.Success).run { copy(isFollowing = !isFollowing) }
                    }
                }
            }
        }.onFailure {
            intent { postSideEffect(DetailSideEffect.ReportError(it)) }
        }
    }

    fun heartExam() = viewModelScope.launch {
        val detailState = container.stateFlow.value
        require(detailState is DetailState.Success)

        intent {
            if (!detailState.isHeart) {
                postHeartUseCase(detailState.exam.id)
                    .onSuccess { heart ->
                        reduce {
                            (state as DetailState.Success).run {
                                copy(exam = exam.copy(heart = heart))
                            }
                        }
                    }.onFailure {
                        postSideEffect(DetailSideEffect.ReportError(it))
                    }
            } else {
                detailState.exam.heart?.id?.also { heartId ->
                    deleteHeartUseCase(heartId)
                        .onSuccess { apiResult ->
                            if (apiResult) {
                                reduce {
                                    (state as DetailState.Success).run {
                                        copy(exam = exam.copy(heart = null))
                                    }
                                }
                            }
                        }.onFailure {
                            postSideEffect(DetailSideEffect.ReportError(it))
                        }
                }
            }
        }
    }

    fun startExam() = viewModelScope.launch {
        intent {
            require(state is DetailState.Success)
            (state as DetailState.Success).run {
                makeExamInstanceUseCase(body = ExamInstanceBody(examId = exam.id)).onSuccess { result ->
                    (state as DetailState.Success).run {
                        postSideEffect(
                            DetailSideEffect.StartExam(
                                result.id,
                                certifyingStatement,
                            ),
                        )
                    }
                }.onFailure {
                    postSideEffect(DetailSideEffect.ReportError(it))
                }
            }
        }
    }
}
