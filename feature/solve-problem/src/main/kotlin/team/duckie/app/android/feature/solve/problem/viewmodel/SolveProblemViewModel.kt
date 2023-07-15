/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.savedstate.getOrThrow
import team.duckie.app.android.common.android.timer.ProblemTimer
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.kotlin.ImmutableList
import team.duckie.app.android.common.kotlin.exception.DuckieClientLogicProblemException
import team.duckie.app.android.domain.examInstance.usecase.GetExamInstanceUseCase
import team.duckie.app.android.domain.quiz.usecase.GetQuizUseCase
import team.duckie.app.android.feature.solve.problem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.solve.problem.viewmodel.state.SolveProblemState
import javax.inject.Inject

@HiltViewModel
internal class SolveProblemViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getExamInstanceUseCase: GetExamInstanceUseCase,
    private val getQuizUseCase: GetQuizUseCase,
) : ViewModel(),
    ContainerHost<SolveProblemState, SolveProblemSideEffect> {
    override val container: Container<SolveProblemState, SolveProblemSideEffect> = container(
        SolveProblemState(),
    )

    init {
        initState()
    }

    private val problemTimer = ProblemTimer(
        coroutineScope = viewModelScope,
    )

    val timerCount: StateFlow<Float> = problemTimer.remainingTime

    fun startTimer(time: Float) {
        problemTimer.start(time)
    }

    fun stopTimer() {
        problemTimer.stop()
    }

    fun initState() = intent {
        val examId = savedStateHandle.getOrThrow<Int>(Extras.ExamId)
        val isQuiz = savedStateHandle.getOrThrow<Boolean>(Extras.IsQuiz)
        reduce {
            state.copy(
                examId = examId,
                isQuiz = isQuiz,
            )
        }
        if (isQuiz) {
            val requirementAnswer = savedStateHandle.getOrThrow<String>(Extras.RequirementAnswer)
            reduce {
                state.copy(requirementAnswer = requirementAnswer)
            }
            getQuizs(examId)
        } else {
            getExams(examId)
        }
    }

    private suspend fun getExams(examId: Int) = intent {
        reduce { state.copy(isProblemsLoading = true, isError = false) }

        getExamInstanceUseCase(id = examId).onSuccess { examInstance ->
            val problemInstances = examInstance.problemInstances?.toImmutableList()
            if (problemInstances == null) {
                stopExam()
            } else {
                reduce {
                    state.copy(
                        isProblemsLoading = false,
                        problems = problemInstances,
                        inputAnswers = ImmutableList(problemInstances.size) { InputAnswer() },
                        totalPage = problemInstances.size,
                    )
                }
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                state.copy(isError = true, isProblemsLoading = false)
            }
            postSideEffect(SolveProblemSideEffect.ReportError(it))
        }
    }

    private suspend fun getQuizs(examId: Int) = intent {
        reduce { state.copy(isProblemsLoading = true, isError = false) }

        getQuizUseCase(examId = examId).onSuccess { quizResult ->
            val quizProblems = quizResult.exam.problems
            if (quizProblems == null) {
                stopExam()
            } else {
                reduce {
                    state.copy(
                        isProblemsLoading = false,
                        quizProblems = quizProblems.toImmutableList(),
                        inputAnswers = ImmutableList(quizProblems.size) { InputAnswer() },
                        totalPage = quizProblems.size,
                        time = quizResult.exam.timer ?: 0,
                    )
                }
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                state.copy(isError = true, isProblemsLoading = false)
            }
            postSideEffect(SolveProblemSideEffect.ReportError(it))
        }
    }

    fun moveNextPage(
        pageIndex: Int,
        inputAnswer: InputAnswer,
        maxPage: Int,
    ) = intent {
        val correctAnswer = state.quizProblems[pageIndex].correctAnswer
            ?: throw DuckieClientLogicProblemException(code = CORRECT_ANSWER_IS_NULL)
        state.quizProblems[pageIndex].answer
        if (correctAnswer.replace(" ", "").lowercase() != inputAnswer.answer.replace(" ", "")
                .lowercase()
        ) {
            finishQuiz(pageIndex, false, inputAnswer.answer)
        } else {
            postSideEffect(SolveProblemSideEffect.MoveNextPage(maxPage))
        }
    }

    fun finishExam(
        userInputAnswers: List<String>,
    ) = intent {
        stopTimer()
        postSideEffect(
            SolveProblemSideEffect.FinishSolveProblem(
                examId = state.examId,
                answers = userInputAnswers,
            ),
        )
    }

    fun finishQuiz(
        index: Int,
        isSuccess: Boolean,
        inputCurrentAnswer: String,
    ) = intent {
        postSideEffect(
            SolveProblemSideEffect.FinishQuiz(
                examId = state.examId,
                time = problemTimer.totalTime.toInt(),
                correctProblemCount = if (isSuccess) { // 현재 페이지 인덱스 == 맞은 개수
                    index + 1
                } else {
                    index
                },
                problemId = if (isSuccess) {
                    null
                } else {
                    state.quizProblems[index].id
                },
                requirementAnswer = state.requirementAnswer,
                inputCurrentAnswer = if (isSuccess) {
                    null
                } else {
                    inputCurrentAnswer
                },
            ),
        )
    }

    fun stopExam() = intent { postSideEffect(SolveProblemSideEffect.NavigatePreviousScreen) }

    companion object {
        private const val CORRECT_ANSWER_IS_NULL = "correct_answer_is_null"
    }
}
