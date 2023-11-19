/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.exam.result.common

import androidx.compose.foundation.BorderStroke
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
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSurface
import team.duckie.app.android.feature.exam.result.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.sugar.QuackPrimaryLargeButton
import team.duckie.quackquack.ui.sugar.QuackSubtitle
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@Composable
internal fun ResultBottomBar(
    isQuiz: Boolean,
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
        if (isQuiz) {
            GrayBorderSmallButton(
                modifier = Modifier
                    .heightIn(min = 44.dp)
                    .weight(1f),
                text = stringResource(id = R.string.exam_result_solve_retry),
                onClick = onClickRetryButton,
            )
        }
        QuackPrimaryLargeButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.exam_result_exit_exam),
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
        border = BorderStroke(width = 1.dp, color = QuackColor.Gray3.value),
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
