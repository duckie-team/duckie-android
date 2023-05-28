/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.start.exam.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.savedstate.getOrThrow
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import javax.inject.Inject

@HiltViewModel
@AllowMagicNumber
internal class StartExamViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<StartExamState, StartExamSideEffect>, ViewModel() {
    override val container = container<StartExamState, StartExamSideEffect>(StartExamState.Loading)

    /** state 를 초기 설정한다. */
    fun initState() {
        intent {
            val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value
            val isQuiz = savedStateHandle.getStateFlow(Extras.IsQuiz, false).value
            if (isQuiz) {
                val requirementQuestion = savedStateHandle.getOrThrow<String>(Extras.RequirementQuestion)
                val requirementPlaceholder = savedStateHandle.getOrThrow<String>(Extras.RequirementPlaceholder)
                val timer = savedStateHandle.getOrThrow<Int>(Extras.Timer)
                reduce {
                    StartExamState.Input(
                        examId = examId,
                        requirementQuestion = requirementQuestion,
                        requirementPlaceholder = requirementPlaceholder,
                        timer = timer,
                        isQuiz = true,
                    )
                }
            } else {
                val certifyingStatement = savedStateHandle.getOrThrow<String>(Extras.CertifyingStatement)
                reduce {
                    StartExamState.Input(
                        examId = examId,
                        certifyingStatement = certifyingStatement,
                        isQuiz = false,
                    )
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
                certified = startExamValidate(),
                examId = inputState.examId,
                isQuiz = inputState.isQuiz,
            ),
        )
    }

    /** 시험 시작 화면을 종료한다. */
    fun finishStartExam() = intent {
        postSideEffect(StartExamSideEffect.FinishStartExam)
    }
}
