/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.dev.mode

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.constant.sharedComposeRString
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.feature.dev.mode.viewmodel.DevModeViewModel
import team.duckie.app.android.feature.dev.mode.viewmodel.state.DevModeState
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

@Composable
fun DevModeDialog(
    viewModel: DevModeViewModel = activityViewModel(),
    visible: Boolean = false,
    onDismiss: () -> Unit,
) {
    val state = viewModel.collectAsState().value
    DuckieDialog(
        container = {
            when (state) {
                is DevModeState.InputPassword -> InputPasswordScreen(
                    inputted = state.inputted,
                    editInputted = viewModel::editInputted,
                    gotoDevMode = viewModel::gotoDevMode,
                )

                is DevModeState.Success -> DevModeScreen()
            }
        },
        leftButtonText = stringResource(id = sharedComposeRString.cancel),
        leftButtonOnClick = {
            viewModel.closeDevMode()
            onDismiss()
        },
        rightButtonText = stringResource(id = sharedComposeRString.check),
        rightButtonOnClick = {
            when (state) {
                is DevModeState.InputPassword -> {
                    viewModel.gotoDevMode()
                }

                is DevModeState.Success -> {

                }
            }
        },
        visible = visible,
        onDismissRequest = {},
    )

}

@Composable
fun InputPasswordScreen(
    inputted: String,
    editInputted: (String) -> Unit,
    gotoDevMode: () -> Unit,
) {
    QuackHeadLine2(
        modifier = Modifier.padding(all = 28.dp),
        text = stringResource(id = R.string.dev_mode_input_password_title),
    )

    QuackNoUnderlineTextField(
        modifier = Modifier.padding(
            start = 28.dp,
            end = 28.dp,
            bottom = 28.dp,
        ),
        text = inputted,
        placeholderText = stringResource(id = R.string.dev_mode_input_password_title),
        onTextChanged = editInputted,
        keyboardActions = KeyboardActions(
            onDone = {
                gotoDevMode()
            },
        )
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
