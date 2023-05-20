/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.exam.result.R
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackSurface

@Composable
internal fun ResultBottomBar(
    onClickRetryButton: () -> Unit,
    onClickExitButton: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        GrayBorderSmallButton(
            modifier = Modifier
                .heightIn(min = 44.dp)
                .weight(1f),
            text = stringResource(id = R.string.solve_retry),
            onClick = onClickRetryButton,
        )
        QuackSmallButton(
            modifier = Modifier
                .heightIn(44.dp)
                .weight(1f),
            type = QuackSmallButtonType.Fill,
            text = stringResource(id = R.string.exit_exam),
            enabled = true,
            onClick = onClickExitButton,
        )
    }
}

@Composable
private fun GrayBorderSmallButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier,
        backgroundColor = QuackColor.White,
        border = QuackBorder(color = QuackColor.Gray3),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackSubtitle(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 12.dp,
            ),
            text = text,
            singleLine = true,
        )
    }
}
