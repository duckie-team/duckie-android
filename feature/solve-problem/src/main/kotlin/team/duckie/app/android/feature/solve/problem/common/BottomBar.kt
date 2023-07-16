/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.common

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
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun ButtonBottomBar(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    isLastPage: Boolean = false,
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
            Spacer(modifier = Modifier)
            QuackAnimatedContent(targetState = isLastPage) {
                when (it) {
                    false -> MediumButton(
                        text = stringResource(id = R.string.next),
                        enabled = enabled,
                        onClick = onRightButtonClick,
                    )

                    true -> MediumButton(
                        text = stringResource(id = R.string.submit),
                        enabled = enabled,
                        onClick = onRightButtonClick,
                        backgroundColor = if (enabled) {
                            QuackColor.DuckieOrange
                        } else {
                            QuackColor.Gray2
                        },
                        border = null,
                        textColor = QuackColor.White,
                    )
                }
            }
        }
    }
}

@Composable
internal fun DoubleButtonBottomBar(
    modifier: Modifier = Modifier,
    isFirstPage: Boolean = false,
    isLastPage: Boolean = false,
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
            QuackAnimatedContent(targetState = isFirstPage) {
                when (it) {
                    true -> {
                        Spacer(modifier = Modifier)
                    }

                    false -> {
                        MediumButton(
                            text = stringResource(id = R.string.previous),
                            onClick = onLeftButtonClick,
                        )
                    }
                }
            }

            QuackAnimatedContent(targetState = isLastPage) {
                when (it) {
                    false -> MediumButton(
                        text = stringResource(id = R.string.next),
                        onClick = onRightButtonClick,
                    )

                    true -> MediumButton(
                        text = stringResource(id = R.string.submit),
                        onClick = onRightButtonClick,
                        backgroundColor = QuackColor.DuckieOrange,
                        border = null,
                        textColor = QuackColor.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun MediumButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    backgroundColor: QuackColor = backgroundFor(enabled),
    border: QuackBorder? = borderFor(enabled),
    textColor: QuackColor = textColorFor(enabled),
) {
    QuackSurface(
        modifier = Modifier,
        backgroundColor = backgroundColor,
        border = border,
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClickFor(enabled, onClick),
    ) {
        QuackText(
            modifier = Modifier.padding(
                vertical = 7.dp,
                horizontal = 12.dp,
            ),
            text = text,
            style = QuackTextStyle.Body1.change(
                color = textColor,
                textAlign = TextAlign.Center,
            ),
            singleLine = true,
        )
    }
}

@Composable
private fun onClickFor(
    enabled: Boolean,
    onClick: (() -> Unit)?,
): (() -> Unit)? {
    return if (enabled) {
        onClick
    } else {
        null
    }
}

@Composable
private fun backgroundFor(enabled: Boolean): QuackColor {
    return if (enabled) {
        QuackColor.White
    } else {
        QuackColor.Gray2
    }
}

@Composable
private fun borderFor(enabled: Boolean): QuackBorder? {
    return if (enabled) {
        QuackBorder(color = QuackColor.Gray3)
    } else {
        null
    }
}

@Composable
private fun textColorFor(enabled: Boolean): QuackColor {
    return if (enabled) {
        QuackColor.Gray1
    } else {
        QuackColor.White
    }
}
