/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.dummyList
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.state.SolveProblemState
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.ImmutableList
import team.duckie.app.android.util.kotlin.copy
import javax.inject.Inject

@HiltViewModel
internal class SolveProblemViewModel @Inject constructor() :
    ViewModel(),
    ContainerHost<SolveProblemState, SolveProblemSideEffect> {
    override val container: Container<SolveProblemState, SolveProblemSideEffect> = container(
        SolveProblemState(),
    )

    @AllowMagicNumber
    fun getProblems() = intent {
        reduce { state.copy(isProblemsLoading = true) }
        delay(1000L)
        reduce {
            state.copy(
                // TODO(EvergreenTree97): 네트워크 통신으로 변경 필요
                isProblemsLoading = false,
                problems = dummyList,
                inputAnswers = ImmutableList(dummyList.size) { InputAnswer() },
            )
        }
    }

    fun moveNextPage() = intent {
        if (state.currentPageIndex < state.totalPage - 1) {
            reduce {
                state.copy(
                    currentPageIndex = state.currentPageIndex.plus(1),
                )
            }
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
