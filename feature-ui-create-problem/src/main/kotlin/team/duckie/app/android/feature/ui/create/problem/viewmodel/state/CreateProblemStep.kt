/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel.state

enum class CreateProblemStep(private val index: Int) {
    ExamInformation(0),
    CreateProblem(1),
    AdditionalInformation(2);

    operator fun minus(previous: Int): CreateProblemStep {
        return values()[index - previous]
    }

    operator fun plus(next: Int): CreateProblemStep {
        return values()[index + next]
    }
}
