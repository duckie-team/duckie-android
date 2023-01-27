/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel.state

import team.duckie.app.android.util.kotlin.AllowMagicNumber

@AllowMagicNumber
enum class CreateProblemStep(private val index: Int) {
    Loading(0),
    ExamInformation(1),
    CreateProblem(2),
    AdditionalInformation(3),
    Search(4),
    Error(5),
    ;

    operator fun minus(previous: Int): CreateProblemStep {
        return values()[index - previous]
    }

    operator fun plus(next: Int): CreateProblemStep {
        return values()[index + next]
    }
}
