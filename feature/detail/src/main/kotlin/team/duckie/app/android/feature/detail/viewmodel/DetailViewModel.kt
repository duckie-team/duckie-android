/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.ui.dialog.ReportAlreadyExists
import team.duckie.app.android.common.kotlin.exception.DuckieResponseFieldNPE
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.usecase.GetExamUseCase
import team.duckie.app.android.domain.examInstance.model.ExamStatus
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceUseCase
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.heart.usecase.DeleteHeartUseCase
import team.duckie.app.android.domain.heart.usecase.PostHeartUseCase
import team.duckie.app.android.domain.quiz.usecase.MakeQuizUseCase
import team.duckie.app.android.domain.report.usecase.ReportUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getExamUseCase: GetExamUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val followUseCase: FollowUseCase,
    private val postHeartUseCase: PostHeartUseCase,
    private val deleteHeartUseCase: DeleteHeartUseCase,
    private val makeExamInstanceUseCase: MakeExamInstanceUseCase,
    private val makeQuizUseCase: MakeQuizUseCase,
    private val reportUseCase: ReportUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<DetailState, DetailSideEffect>, ViewModel() {
    override val container = container<DetailState, DetailSideEffect>(DetailState.Loading)

    private val initialExamId: Int = -1

    private val dynamicExamId =
        savedStateHandle.getStateFlow(Extras.DynamicLinkExamId, initialExamId).value

    init {
        launchValid {
            val examId = savedStateHandle.getStateFlow(Extras.ExamId, initialExamId).value
            val examStatus =
                savedStateHandle.getStateFlow(Extras.ExamStatus, ExamStatus.Ready).value
            initExam(examId, examStatus)
        }
    }

    fun parseDynamicLink() {
        if (dynamicExamId != initialExamId) {
            initExam(dynamicExamId, null)
        }
    }

    /**
     * App Link 형식으로 넘어왔을 경우 [block] 을 싱행하지 않습니다.
     */
    private fun launchValid(block: () -> Unit) {
        if (dynamicExamId == initialExamId) {
            block()
        }
    }

    private fun initExam(examId: Int, examStatus: ExamStatus?) = intent {
        val exam = getExamUseCase(examId).getOrElse {
            postSideEffect(DetailSideEffect.ReportError(it))
            null
        }
        getMeUseCase()
            .onSuccess { me ->
                reduce {
                    if (exam != null) {
                        DetailState.Success(
                            exam = exam,
                            appUser = me,
                            isFollowing = exam.user?.follow != null,
                            examStatus = examStatus ?: (ExamStatus.from(exam.status ?: "")
                                ?: ExamStatus.Ready),
                        )
                    } else {
                        DetailState.Error(DuckieResponseFieldNPE("exam or me is Null"))
                    }
                }
            }.onFailure {
                postSideEffect(DetailSideEffect.ReportError(it))
            }
    }

    fun refresh() {
        container.stateFlow.value.let { state ->
            if (state is DetailState.Success) {
                initExam(state.exam.id, state.examStatus)
            }
        }
    }

    fun copyExamDynamicLink(examId: Int) = intent {
        postSideEffect(DetailSideEffect.CopyExamIdDynamicLink(examId))
    }

    fun pullToRefresh() = intent {
        updateIsRefreshing(true)
        refresh()
        updateIsRefreshing(false)
    }

    /** [examId] 게시글을 신고한다. */
    fun report(examId: Int) = intent {
        reportUseCase(examId = examId)
            .onSuccess {
                updateReportDialogVisible(true)
            }
            .onFailure { exception ->
                when {
                    exception.isReportAlreadyExists -> postSideEffect(
                        DetailSideEffect.SendToast(ReportAlreadyExists),
                    )

                    else -> postSideEffect(DetailSideEffect.ReportError(exception))
                }
            }
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
            val successState = state as DetailState.Success
            successState.run {
                when {
                    examStatus == ExamStatus.Funding -> {
                        // TODO(riflockle7): 시험 시작이 아닌 문제 생성으로 넘어가야 함
                    }

                    isQuiz -> {
                        makeQuizUseCase(examId = exam.id)
                            .onSuccess { result ->
                                postSideEffect(
                                    DetailSideEffect.StartQuiz(
                                        examId = result.id,
                                        requirementQuestion = exam.requirementQuestion ?: "",
                                        requirementPlaceholder = exam.requirementPlaceholder ?: "",
                                        timer = exam.timer ?: 0,
                                        isQuiz = isQuiz,
                                    ),
                                )
                            }.onFailure {
                                postSideEffect(DetailSideEffect.ReportError(it))
                            }
                    }

                    !isQuiz -> {
                        makeExamInstanceUseCase(body = ExamInstanceBody(examId = exam.id)).onSuccess { result ->
                            when (result.status) {
                                ExamStatus.Ready -> {
                                    postSideEffect(
                                        DetailSideEffect.StartExam(
                                            examId = result.id,
                                            certifyingStatement = certifyingStatement,
                                            isQuiz = isQuiz,
                                        ),
                                    )
                                }

                                ExamStatus.Submitted -> {
                                    postSideEffect(
                                        DetailSideEffect.NavigateToExamResult(result.id),
                                    )
                                }

                                else -> {}
                            }
                        }.onFailure {
                            postSideEffect(DetailSideEffect.ReportError(it))
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun goToSearch(tag: String) = viewModelScope.launch {
        intent { postSideEffect(DetailSideEffect.NavigateToSearch(tag)) }
    }

    fun goToProfile(userId: Int) = viewModelScope.launch {
        intent { postSideEffect(DetailSideEffect.NavigateToMyPage(userId)) }
    }

    fun updateReportDialogVisible(visible: Boolean) = intent {
        reduce {
            require(state is DetailState.Success)
            (state as DetailState.Success).run {
                copy(reportDialogVisible = visible)
            }
        }
    }

    private suspend fun SimpleSyntax<DetailState, *>.updateIsRefreshing(isRefreshing: Boolean) =
        reduce {
            require(state is DetailState.Success)
            (state as DetailState.Success).run {
                copy(isRefreshing = isRefreshing)
            }
        }
}
