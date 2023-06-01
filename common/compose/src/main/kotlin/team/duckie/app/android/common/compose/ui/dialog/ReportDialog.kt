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
fun ReportDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    DuckieDialog(
        title = stringResource(id = R.string.report_success),
        rightButtonText = stringResource(id = R.string.check),
        rightButtonOnClick = onClick,
        visible = visible,
        onDismissRequest = onDismissRequest,
    )
}
