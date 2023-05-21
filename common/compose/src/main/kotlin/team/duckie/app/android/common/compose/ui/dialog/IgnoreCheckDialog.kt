/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import team.duckie.app.android.common.compose.R

@Composable
fun IgnoreCheckDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    targetName: String,
    onIgnoreRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    DuckieDialog(
        modifier = modifier.duckieDialogPosition(DuckieDialogPosition.CENTER),
        title = stringResource(id = R.string.ignore_check_title, targetName),
        message = stringResource(id = R.string.ignore_check_content, targetName),
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = onDismissRequest,
        rightButtonText = stringResource(id = R.string.check),
        rightButtonOnClick = onIgnoreRequest,
        visible = visible,
        onDismissRequest = onDismissRequest,
    )
}
