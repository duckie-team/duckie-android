/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class CreateProblemState(
    val createProblemStep: CreateProblemStep = CreateProblemStep.ExamInformation,
    val examInformation: ExamInformation = ExamInformation(),
) {
    data class ExamInformation(
        val categories: ImmutableList<String> = persistentListOf(
            "연예인",
            "영화",
            "만화/애니",
            "웹툰",
            "게임",
            "밀리터리"
        ),
        val categoriesSelection: ImmutableList<Boolean> = List(categories.size) { false }.toImmutableList(),
        val examArea: String = "",
        val examTitle: String = "",
        val examDescription: String = "",
        val certifyingStatement: String = ""
    )
}


