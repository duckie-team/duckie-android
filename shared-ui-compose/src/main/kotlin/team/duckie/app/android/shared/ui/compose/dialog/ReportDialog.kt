/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.shared.ui.compose.dialog

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import team.duckie.app.android.shared.ui.compose.R

@Composable
fun ReportDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    DuckieDialog(
        modifier = modifier.duckieDialogPosition(DuckieDialogPosition.CENTER),
        title = stringResource(id = R.string.report_success),
        rightButtonText = stringResource(id = R.string.check),
        rightButtonOnClick = onClick,
        visible = visible,
        onDismissRequest = onDismissRequest,
    )
}
