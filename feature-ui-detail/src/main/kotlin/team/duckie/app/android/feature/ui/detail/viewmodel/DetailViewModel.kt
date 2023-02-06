/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(OutOfDateApi::class)

package team.duckie.app.android.feature.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.heart.model.HeartsBody
import team.duckie.app.android.domain.heart.usecase.PostHeartsUseCase
import team.duckie.app.android.domain.heart.usecase.DeleteHeartsUseCase
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.util.kotlin.DuckieResponseFieldNPE
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject
import team.duckie.app.android.feature.datastore.me

const val DelayTime = 2000L

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val followUseCase: FollowUseCase,
    private val postHeartsUseCase: PostHeartsUseCase,
    private val deleteHeartsUseCase: DeleteHeartsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<DetailState, DetailSideEffect>, ViewModel() {
    override val container = container<DetailState, DetailSideEffect>(DetailState.Loading)

    suspend fun initState() {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value

        val exam = runCatching { examRepository.getExam(examId) }.getOrNull()
        delay(DelayTime)
        intent {
            reduce {
                if (exam != null) {
                    DetailState.Success(exam, me)
                } else {
                    DetailState.Error(DuckieResponseFieldNPE("exam is Null"))
                }
            }
        }
    }

    @OptIn(OutOfDateApi::class)
    fun followUser() = viewModelScope.launch {
        val detailState = container.stateFlow.value
        require(detailState is DetailState.Success)

        followUseCase(
            FollowBody(detailState.appUser.id, detailState.exam.user.id),
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
                postHeartsUseCase(detailState.exam.id)
                    .onSuccess { heartId ->
                        reduce {
                            (state as DetailState.Success).run { copy(heartId = heartId) }
                        }
                    }.onFailure {
                        postSideEffect(DetailSideEffect.ReportError(it))
                    }
            } else {
                deleteHeartsUseCase(HeartsBody(detailState.exam.id, detailState.heartId))
                    .onSuccess { apiResult ->
                        if (apiResult) {
                            reduce {
                                (state as DetailState.Success).run { copy(heartId = null) }
                            }
                        }
                    }.onFailure {
                        postSideEffect(DetailSideEffect.ReportError(it))
                    }
            }
        }
    }

    fun startExam() = viewModelScope.launch {
        intent {
            require(state is DetailState.Success)
            postSideEffect(
                (state as DetailState.Success).run {
                    DetailSideEffect.StartExam(
                        exam.id,
                        exam.certifyingStatement,
                    )
                },
            )
        }
    }
}
