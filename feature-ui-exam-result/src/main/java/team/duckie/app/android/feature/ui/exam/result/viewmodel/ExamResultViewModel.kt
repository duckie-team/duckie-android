/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.viewmodel

import android.util.Log
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
import team.duckie.app.android.domain.quiz.usecase.UpdateQuizUseCase
import team.duckie.app.android.util.kotlin.exception.DuckieClientLogicProblemException
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
class ExamResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val makeExamInstanceSubmitUseCase: MakeExamInstanceSubmitUseCase,
) : ViewModel(),
    ContainerHost<ExamResultState, ExamResultSideEffect> {

    companion object {
        private const val FAILED_IMPORT_EXTRAS = "failed_import_extra"
    }

    override val container: Container<ExamResultState, ExamResultSideEffect> = container(
        ExamResultState.Loading,
    )

    fun initState() {
        val examId = savedStateHandle.get<Int>(Extras.ExamId)
            ?: throw DuckieClientLogicProblemException(code = FAILED_IMPORT_EXTRAS)
        val isQuiz =
            savedStateHandle.get<Boolean>(Extras.IsQuiz) ?: throw DuckieClientLogicProblemException(
                code = FAILED_IMPORT_EXTRAS
            )
        if (isQuiz) {
            val updateQuizParam =
                savedStateHandle.get<UpdateQuizUseCase.Param>(Extras.UpdateQuizParam).also {
                    Log.d("ExamResultViewModel", "initState: ${it.toString()}")
                }
        } else {
            val submitted = savedStateHandle.get<Array<String>>(Extras.Submitted)?.toList()
                ?: throw DuckieClientLogicProblemException(code = FAILED_IMPORT_EXTRAS)
            getReport(examId, ExamInstanceSubmitBody(submitted = submitted.toImmutableList()))
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
                ExamResultState.Success(reportUrl = submit.examScoreImageUrl)
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                ExamResultState.Error(exception = it)
            }
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
