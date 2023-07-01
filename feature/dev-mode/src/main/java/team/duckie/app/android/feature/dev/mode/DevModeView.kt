/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.dev.mode

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.feature.dev.mode.viewmodel.DevModeViewModel
import team.duckie.app.android.feature.dev.mode.viewmodel.state.SkeletonState
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

@Composable
fun DevModeDialog(
    viewModel: DevModeViewModel = activityViewModel(),
    visible: Boolean = false,
    onDismiss: () -> Unit,
) {
    DuckieDialog(
        container = {
            when (viewModel.collectAsState().value) {
                is SkeletonState.InputPassword -> InputPasswordScreen()
                is SkeletonState.Success -> DevModeScreen()
            }
        },
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = onDismiss,
        rightButtonText = stringResource(id = R.string.check),
        rightButtonOnClick = { },
        visible = visible,
        onDismissRequest = onDismiss,
    )

}

@Composable
fun InputPasswordScreen() {
    QuackText(
        modifier = Modifier,
        text = "InputPasswordScreen",
        typography = QuackTypography.Body1,
    )
}

@Composable
fun DevModeScreen() {
    QuackText(
        modifier = Modifier,
        text = "DevModeScreen",
        typography = QuackTypography.Body1,
    )
}
