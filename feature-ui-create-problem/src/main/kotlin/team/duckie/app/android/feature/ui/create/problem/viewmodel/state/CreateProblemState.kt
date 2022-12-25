/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.category.model.Category

data class CreateProblemState(
    val createProblemStep: CreateProblemStep = CreateProblemStep.CreateProblem,
    val examInformation: ExamInformation = ExamInformation(),
    val error: ExamInformation.Error? = null,
) {
    data class ExamInformation(
        val isCategoryLoading: Boolean = true,
        val categories: ImmutableList<Category> = persistentListOf(),
        val categorySelection: Int = -1,
        val isExamAreaSelected: Boolean = false,
        val examTitle: String = "",
        val examDescription: String = "",
        val certifyingStatement: String = "",
        val foundExamArea: FoundExamArea = FoundExamArea(),
        val scrollPosition: Int = 0,
        val examDescriptionFocused: Boolean = false,
        val additionalInfoArea: AdditionInfoArea = AdditionInfoArea(),
    ) {
        val examArea: String
            get() = foundExamArea.examArea

        data class FoundExamArea(
            val searchResults: ImmutableList<String> = persistentListOf(
                // TODO(EvergreenTree97): Server Request
                "도로",
                "도로 주행",
                "도로 셀카",
                "도로 패션",
            ),
            val examArea: String = "",
            val cursorPosition: Int = 0,
        )
        data class AdditionInfoArea(
            val thumbnail: Any? = null,
            val takeTitle: String = "",
            val tempTag: String = "",
            val tags: ImmutableList<String> = persistentListOf(),
        )
        data class Error(val throwable: Throwable?)
    }
}
