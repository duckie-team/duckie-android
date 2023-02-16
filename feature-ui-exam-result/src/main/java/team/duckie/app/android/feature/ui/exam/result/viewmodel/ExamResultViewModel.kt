/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceSubmitUseCase
import team.duckie.app.android.util.kotlin.DuckieClientLogicProblemException
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
class ExamResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val makeExamInstanceSubmitUseCase: MakeExamInstanceSubmitUseCase,
) : ViewModel(),
    ContainerHost<ExamResultState, ExamResultSideEffect> {
    override val container: Container<ExamResultState, ExamResultSideEffect> = container(
        ExamResultState(),
    )

    fun initState() {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value
        val submitted =
            savedStateHandle.getStateFlow(Extras.Submitted, emptyArray<String>()).value.toList()

        if (examId == -1 || submitted.isEmpty()) {
            throw DuckieClientLogicProblemException(code = "failed_import_extra")
        } else {
            getReport(examId, ExamInstanceSubmitBody(submitted = submitted.toImmutableList()))
        }
    }

    private fun getReport(
        examId: Int,
        submitted: ExamInstanceSubmitBody,
    ) = intent {
        reduce {
            state.copy(isReportLoading = true)
        }
        makeExamInstanceSubmitUseCase(
            id = examId,
            body = submitted,
        ).onSuccess { submit: ExamInstanceSubmit ->
            reduce {
                state.copy(
                    isReportLoading = false,
                    reportUrl = submit.examScoreImageUrl,
                )
            }
        }.onFailure {
            it.printStackTrace()
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    fun clickRetry(message: String) = intent {
        postSideEffect(ExamResultSideEffect.SendToast(message))
    }

    fun exitExam() = intent {
        postSideEffect(ExamResultSideEffect.FinishExamResult)
    }
}
