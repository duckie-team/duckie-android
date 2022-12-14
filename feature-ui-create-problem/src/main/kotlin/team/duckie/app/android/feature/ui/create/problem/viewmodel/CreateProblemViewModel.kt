/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel

import android.util.Log
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.model.ProblemItem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.Tag
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemState
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.fastAny
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateProblemViewModel @Inject constructor(
    private val makeExamUseCase: MakeExamUseCase
) : BaseViewModel<CreateProblemState, CreateProblemSideEffect>(CreateProblemState()) {
    private val TAG = CreateProblemViewModel::class.simpleName

    /*suspend fun makeExam() {
        makeExamUseCase(dummyParam).onSuccess { exam ->
            Log.d(TAG, exam.toString() + "성공")
        }
    }*/

    fun onClickCategory(
        index: Int,
    ) {
        updateState { prevState ->
            CreateProblemState(
                examInformation = CreateProblemState.ExamInformation(
                    categoriesSelection = prevState.examInformation.categoriesSelection.copy {
                        this[index] = !this[index]
                    }.toImmutableList()
                ),
            )
        }
    }

    fun setExamArea(examArea: String) {
        updateState {
            CreateProblemState(
                examInformation = CreateProblemState.ExamInformation(
                    examArea = examArea,
                ),
            )
        }
    }

    fun setExamTitle(examTitle: String) {
        updateState {
            CreateProblemState(
                examInformation = CreateProblemState.ExamInformation(
                    examTitle = examTitle,
                ),
            )
        }
    }

    fun setExamDescription(examDescription: String) {
        updateState {
            CreateProblemState(
                examInformation = CreateProblemState.ExamInformation(
                    examDescription = examDescription,
                ),
            )
        }
    }

    fun setCertifyingStatement(certifyingStatement: String) {
        updateState {
            CreateProblemState(
                examInformation = CreateProblemState.ExamInformation(
                    certifyingStatement = certifyingStatement,
                ),
            )
        }
    }

    fun isAllFieldsNotEmpty(): Boolean {
        return with(currentState.examInformation) {
            categoriesSelection.fastAny { true } && examArea.isNotEmpty() && examTitle.isNotEmpty() && examDescription.isNotEmpty() && certifyingStatement.isNotEmpty()
        }
    }
}
