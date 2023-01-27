/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.component.QuackTitle1

@Composable
internal fun ErrorScreen(
    viewModel: CreateProblemViewModel = activityViewModel(),
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val isEditMode = viewModel.collectAsState().value.isEditMode
        QuackTitle1(
            text = "에러입니다\n\n$viewModel\n\n${
                if (isEditMode) {
                    stringResource(id = R.string.get_exam_authorized_error)
                } else {
                    ""
                }
            }",
        )
    }
}
