/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.exam.model.Category

data class CreateProblemState(
    val createProblemStep: CreateProblemStep = CreateProblemStep.ExamInformation,
    val examInformation: ExamInformation = ExamInformation(),
) {
    data class ExamInformation(
        val isCategoryLoading: Boolean = false,
        val categories: ImmutableList<Category> = persistentListOf(),
        val categorySelection: Int = -1,
        val isExamAreaSelected: Boolean = false,
        val examTitle: String = "",
        val examDescription: String = "",
        val certifyingStatement: String = "",
        val foundExamArea: FoundExamArea = FoundExamArea(),
        val scrollPosition: Int = 0,
        val examDescriptionFocused: Boolean = false,
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
        )
    }
}
