/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel

@Composable
internal fun CreateProblemScreen(
    viewModel: CreateProblemViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.makeExam()
    }
}
