/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.examInstance.usecase.GetExamInstanceUseCase
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.state.SolveProblemState
import team.duckie.app.android.util.kotlin.DuckieClientLogicProblemException
import team.duckie.app.android.util.kotlin.ImmutableList
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
internal class SolveProblemViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getExamInstanceUseCase: GetExamInstanceUseCase,
) : ViewModel(),
    ContainerHost<SolveProblemState, SolveProblemSideEffect> {
    override val container: Container<SolveProblemState, SolveProblemSideEffect> = container(
        SolveProblemState(),
    )

    suspend fun initState() = intent {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value
        if (examId != -1) {
            reduce { state.copy(examId = examId) }
            getProblems(examId)
        } else {
            throw DuckieClientLogicProblemException(code = "failed_import_extra")
        }
    }

    private suspend fun getProblems(examId: Int) = intent {
        reduce { state.copy(isProblemsLoading = true) }
        getExamInstanceUseCase(id = examId).onSuccess { examInstance ->
            reduce {
                val problemInstances =
                    examInstance.problemInstances?.toImmutableList() ?: persistentListOf()
                state.copy(
                    isProblemsLoading = false,
                    problems = problemInstances,
                    inputAnswers = ImmutableList(problemInstances.size) { InputAnswer() },
                )
            }
        }.onFailure {
            it.printStackTrace()
            postSideEffect(SolveProblemSideEffect.ReportError(it))
        }
    }

    fun moveNextPage() = intent {
        if (state.currentPageIndex < state.totalPage - 1) {
            reduce {
                state.copy(
                    currentPageIndex = state.currentPageIndex.plus(1),
                )
            }
        } else {
            postSideEffect(
                SolveProblemSideEffect.FinishSolveProblem(
                    examId = state.examId,
                    answers = state.inputAnswers.fastMap { it.answer },
                ),
            )
        }
    }

    fun movePreviousPage() = intent {
        if (state.currentPageIndex > 0) {
            reduce {
                state.copy(
                    currentPageIndex = state.currentPageIndex.minus(1),
                )
            }
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
}
