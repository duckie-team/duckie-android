/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength")

package team.duckie.app.android.feature.ui.create.problem.viewmodel

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.fastAny
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateProblemViewModel @Inject constructor(
    //private val makeExamUseCase: MakeExamUseCase,
) : BaseViewModel<CreateProblemState, CreateProblemSideEffect>(CreateProblemState()) {

    /*suspend fun makeExam() {
        makeExamUseCase(dummyParam).onSuccess { exam ->
            Log.d(TAG, exam.toString() + "성공")
        }
    }*/

    fun onClickCategory(
        index: Int,
    ) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    categoriesSelection = prevState.examInformation.categoriesSelection.copy {
                        this[index] = !this[index]
                    }.toImmutableList(),
                ),
            )
        }
    }

    fun navigateStep(step: CreateProblemStep) {
        updateState { prevState ->
            prevState.copy(
                createProblemStep = step,
            )
        }
    }

    fun setExamTitle(examTitle: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    examTitle = examTitle,
                ),
            )
        }
    }

    fun setExamDescription(examDescription: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    examDescription = examDescription,
                ),
            )
        }
    }

    fun setCertifyingStatement(certifyingStatement: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    certifyingStatement = certifyingStatement,
                ),
            )
        }
    }

    fun setExamArea(examArea: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    foundExamArea = prevState.examInformation.foundExamArea.copy(
                        examArea = examArea,
                    )
                ),
            )
        }
    }

    fun clickSearchList(index: Int) {
        updateState { prevState ->
            prevState.copy(
                createProblemStep = CreateProblemStep.ExamInformation,
                examInformation = prevState.examInformation.run {
                    copy(
                        foundExamArea = foundExamArea.copy(
                            examArea = foundExamArea.searchResults[index]
                        )
                    )
                },
            )
        }
    }

    fun isAllFieldsNotEmpty(): Boolean {
        return with(currentState.examInformation) {
            categoriesSelection.fastAny { it } && examArea.isNotEmpty() && examTitle.isNotEmpty() && examDescription.isNotEmpty() && certifyingStatement.isNotEmpty()
        }
    }
}
