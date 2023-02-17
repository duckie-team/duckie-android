/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.solve.problem.R
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun DoubleButtonBottomBar(
    modifier: Modifier = Modifier,
    isFirstPage: Boolean = false,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        QuackDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 9.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackAnimatedVisibility(visible = !isFirstPage) {
                MediumButton(
                    text = stringResource(id = R.string.previous),
                    onClick = onLeftButtonClick,
                )
            }
            if (isFirstPage) {
                Spacer(modifier = Modifier)
            }
            MediumButton(
                text = stringResource(id = R.string.no_idea),
                onClick = onRightButtonClick,
            )
        }
    }
}

@Composable
private fun MediumButton(
    text: String,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = Modifier,
        backgroundColor = QuackColor.White,
        border = QuackBorder(color = QuackColor.Gray3),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackText(
            modifier = Modifier.padding(
                vertical = 7.dp,
                horizontal = 12.dp,
            ),
            text = text,
            style = QuackTextStyle.Body1.change(
                color = QuackColor.Gray1,
                textAlign = TextAlign.Center,
            ),
            singleLine = true,
        )
    }
}
