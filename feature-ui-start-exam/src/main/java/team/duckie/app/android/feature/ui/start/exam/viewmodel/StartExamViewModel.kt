/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.exception.DuckieClientLogicProblemException
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
@AllowMagicNumber
internal class StartExamViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<StartExamState, StartExamSideEffect>, ViewModel() {
    override val container = container<StartExamState, StartExamSideEffect>(StartExamState.Loading)

    /** state 를 초기 설정한다. */
    fun initState() {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value
        val certifyingStatement = savedStateHandle
            .getStateFlow(Extras.CertifyingStatement, "").value

        intent {
            reduce {
                if (examId == -1 || certifyingStatement == "") {
                    StartExamState.Error(DuckieClientLogicProblemException(code = ""))
                } else {
                    StartExamState.Input(examId, certifyingStatement)
                }
            }
        }
    }

    /** 필적 확인 문구를 입력한다. */
    fun inputCertifyingStatement(text: String) = intent {
        reduce {
            (state as StartExamState.Input).copy(certifyingStatementInputText = text)
        }
    }

    /** validate 를 체크한다. */
    fun startExamValidate(): Boolean {
        return (container.stateFlow.value as? StartExamState.Input)?.isCertified == true
    }

    /** 문제 풀기 화면을 실행한다 */
    fun startSolveProblem() = intent {
        val inputState = state as StartExamState.Input
        postSideEffect(
            StartExamSideEffect.NavigateToSolveProblem(
                startExamValidate(),
                inputState.examId,
            ),
        )
    }

    /** 시험 시작 화면을 종료한다. */
    fun finishStartExam() = intent {
        postSideEffect(StartExamSideEffect.FinishStartExam)
    }
}
