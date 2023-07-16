/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.savedstate.getOrThrow
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.examInstance.model.ExamInstance
import team.duckie.app.android.domain.examInstance.usecase.GetExamInstanceUseCase
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceSubmitUseCase
import team.duckie.app.android.domain.quiz.usecase.GetQuizUseCase
import team.duckie.app.android.domain.quiz.usecase.MakeQuizUseCase
import team.duckie.app.android.domain.quiz.usecase.PostQuizReactionUseCase
import team.duckie.app.android.domain.quiz.usecase.SubmitQuizUseCase
import javax.inject.Inject

@HiltViewModel
class ExamResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val makeExamInstanceSubmitUseCase: MakeExamInstanceSubmitUseCase,
    private val getExamInstanceUseCase: GetExamInstanceUseCase,
    private val submitQuizUseCase: SubmitQuizUseCase,
    private val getQuizUseCase: GetQuizUseCase,
    private val makeQuizUseCase: MakeQuizUseCase,
    private val postQuizReactionUseCase: PostQuizReactionUseCase,
) : ViewModel(),
    ContainerHost<ExamResultState, ExamResultSideEffect> {

    override val container: Container<ExamResultState, ExamResultSideEffect> = container(
        ExamResultState.Loading,
    )

    private fun postQuizReaction() = intent {
        val state = state as ExamResultState.Success
        postQuizReactionUseCase(
            examId = state.examId,
            reaction = state.reaction,
        )
    }

    fun initState() {
        val examId = savedStateHandle.getOrThrow<Int>(Extras.ExamId)
        val isQuiz = savedStateHandle.getOrThrow<Boolean>(Extras.IsQuiz)
        if (isQuiz) {
            val updateQuizParam =
                savedStateHandle.getOrThrow<SubmitQuizUseCase.Param>(Extras.UpdateQuizParam)
            updateQuiz(
                examId = examId,
                updateQuizParam = updateQuizParam,
            )
        } else {
            val submitted =
                savedStateHandle.getStateFlow<Array<String>>(Extras.Submitted, emptyArray()).value
            val isPassed = savedStateHandle.getOrThrow<Boolean>(Extras.IsPassed)
            if (isPassed) {
                getReportWhenAlreadySolved(examId = examId)
            } else {
                getReport(
                    examId = examId,
                    submitted = ExamInstanceSubmitBody(
                        submitted = submitted.toList().toImmutableList(),
                    ),
                )
            }
        }
    }

    private fun getReport(
        examId: Int,
        submitted: ExamInstanceSubmitBody,
    ) = intent {
        reduce {
            ExamResultState.Loading
        }
        makeExamInstanceSubmitUseCase(
            id = examId,
            body = submitted,
        ).onSuccess { submit: ExamInstanceSubmit ->
            reduce {
                ExamResultState.Success(
                    reportUrl = submit.examScoreImageUrl,
                    isQuiz = false,
                )
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                ExamResultState.Error(exception = it)
            }
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    private fun getReportWhenAlreadySolved(examId: Int) = intent {
        reduce { ExamResultState.Loading }
        getExamInstanceUseCase(examId).onSuccess { result: ExamInstance ->
            reduce {
                ExamResultState.Success(
                    reportUrl = result.scoreImageUrl ?: "",
                    isQuiz = false,
                )
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                ExamResultState.Error(exception = it)
            }
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    private fun updateQuiz(
        examId: Int,
        updateQuizParam: SubmitQuizUseCase.Param,
    ) = intent {
        reduce {
            ExamResultState.Loading
        }
        viewModelScope.launch {
            submitQuizUseCase(examId, updateQuizParam).onFailure {
                it.printStackTrace()
                reduce {
                    ExamResultState.Error(exception = it)
                }
                postSideEffect(ExamResultSideEffect.ReportError(it))
            }
        }.join()
        viewModelScope.launch {
            getQuizUseCase(examId).onSuccess { quizResult ->
                val isPerfectScore = quizResult.wrongProblem == null
                reduce {
                    with(quizResult) {
                        ExamResultState.Success(
                            examId = id,
                            reportUrl = if (isPerfectScore) {
                                exam.perfectScoreImageUrl ?: ""
                            } else {
                                wrongProblem?.solution?.solutionImageUrl ?: ""
                            },
                            isQuiz = true,
                            correctProblemCount = correctProblemCount,
                            time = time,
                            mainTag = exam.mainTag?.name ?: "",
                            ranking = ranking ?: 0,
                            wrongAnswerMessage = wrongProblem?.solution?.wrongAnswerMessage ?: "",
                            requirementPlaceholder = exam.requirementPlaceholder ?: "",
                            requirementQuestion = exam.requirementQuestion ?: "",
                            timer = exam.timer ?: 0,
                            originalExamId = exam.id,
                            isPerfectScore = isPerfectScore,
                            nickname = user.nickname,
                            thumbnailUrl = exam.thumbnailUrl,
                            solvedCount = exam.solvedCount ?: 0,
                            isBestRecord = isBestRecord,

                            )
                    }
                }
            }.onFailure {
                it.printStackTrace()
                reduce {
                    ExamResultState.Error(exception = it)
                }
                postSideEffect(ExamResultSideEffect.ReportError(it))
            }
        }
    }

    fun updateReaction(reaction: String) = intent {
        val state = state as ExamResultState.Success

        reduce {
            state.copy(
                reaction = reaction,
            )
        }
    }

    fun clickRetry() = intent {
        val state = state as ExamResultState.Success
        postReaction()
        makeQuizUseCase(examId = state.originalExamId).onSuccess { result ->
            postSideEffect(
                ExamResultSideEffect.NavigateToStartExam(
                    examId = result.id,
                    requirementQuestion = state.requirementQuestion,
                    requirementPlaceholder = state.requirementPlaceholder,
                    timer = state.timer,
                ),
            )
        }.onFailure {
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    fun updateExamResultScreen(screen: ExamResultScreen) = intent {
        require(state is ExamResultState.Success)
        reduce {
            (state as ExamResultState.Success).copy(
                currentScreen = screen,
            )
        }
    }

    private fun postReaction() = intent {
        val state = state as ExamResultState.Success
        if (state.isBestRecord && state.reaction.isNotEmpty()) {
            postQuizReaction()
        }
    }

    fun exitExam() = intent {
        postReaction()
        postSideEffect(ExamResultSideEffect.FinishExamResult)
    }
}
