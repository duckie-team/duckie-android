/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.viewmodel

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
import team.duckie.app.android.domain.examInstance.usecase.GetExamInstanceUseCase
import team.duckie.app.android.domain.quiz.usecase.GetQuizUseCase
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.SolveProblemState
import team.duckie.app.android.util.android.timer.ProblemTimer
import team.duckie.app.android.util.kotlin.ImmutableList
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.exception.DuckieClientLogicProblemException
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.ui.const.Extras
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

    companion object {
        internal const val TimerCount = 10
        internal val DuringMillis = 1.seconds
        private const val FAILED_IMPORT_EXTRA = "failed_import_extra"
        private const val CORRECT_ANSWER_IS_NULL = "correct_answer_is_null"
    }

    private val problemTimer = ProblemTimer(
        count = TimerCount,
        coroutineScope = viewModelScope,
        duringMillis = DuringMillis,
    )

    val timerCount: StateFlow<Int> = problemTimer.remainingTime

    fun startTimer() {
        problemTimer.start()
    }

    fun stopTimer() {
        problemTimer.stop()
    }

    fun initState() = intent {
        val examId = savedStateHandle.get<Int>(Extras.ExamId)
            ?: throw DuckieClientLogicProblemException(code = FAILED_IMPORT_EXTRA)
        val isQuiz = savedStateHandle.get<Boolean>(Extras.IsQuiz)
            ?: throw DuckieClientLogicProblemException(code = FAILED_IMPORT_EXTRA)
        reduce {
            state.copy(isQuiz = isQuiz)
        }
        if (isQuiz) {
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

    fun inputAnswer(
        pageIndex: Int,
        inputAnswer: InputAnswer,
    ) = intent {
        reduce {
            state.copy(
                inputAnswers = state.inputAnswers.copy {
                    this[pageIndex] = inputAnswer
                },
            )
        }
    }

    fun moveNextPage(
        pageIndex: Int,
    ) = intent {
        val correctAnswer = state.quizProblems[pageIndex].correctAnswer
            ?: throw DuckieClientLogicProblemException(code = CORRECT_ANSWER_IS_NULL)
        if (correctAnswer != state.inputAnswers[pageIndex].answer) {
            finishQuiz(pageIndex)
        }
    }

    fun finishExam() = intent {
        stopTimer()
        postSideEffect(
            SolveProblemSideEffect.FinishSolveProblem(
                examId = state.examId,
                answers = state.inputAnswers.fastMap { it.answer },
            ),
        )
    }

    fun finishQuiz(index: Int) = intent {
        postSideEffect(
            SolveProblemSideEffect.FinishQuiz(
                examId = state.examId,
                time = problemTimer.totalTime,
                correctProblemCount = index, // 현재 페이지 인덱스 == 맞은 개수
                problemId = state.quizProblems[index].id,
            ),
        )
    }

    fun stopExam() = intent { postSideEffect(SolveProblemSideEffect.NavigatePreviousScreen) }

}
