/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.common

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSurface
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun MusicDoubleButtonBottomBar(
    modifier: Modifier = Modifier,
    remainCount: Int,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        QuackMaxWidthDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MediumButton(
                text = stringResource(id = R.string.give_up),
                onClick = onLeftButtonClick,
                border = QuackBorder(
                    color = QuackColor.Gray3,
                ),
                backgroundColor = QuackColor.White,
            )
            MediumButton(
                modifier = Modifier.weight(1f),
                text = stringResource(
                    id = R.string.submit_music_with_remain_count,
                    remainCount.toString(),
                ),
                onClick = onRightButtonClick,
                backgroundColor = QuackColor.DuckieOrange,
                textColor = QuackColor.White,
            )
        }
    }
}

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
        QuackMaxWidthDivider()
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
            AnimatedContent(
                targetState = isLastPage,
                label = "AnimatedContent",
            ) {
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
        QuackMaxWidthDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 9.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AnimatedContent(
                targetState = isFirstPage,
                label = "AnimatedContent",
            ) {
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

            AnimatedContent(
                targetState = isLastPage,
                label = "AnimatedContent",
            ) {
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
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    backgroundColor: QuackColor = backgroundFor(enabled),
    border: QuackBorder? = borderFor(enabled),
    textColor: QuackColor = textColorFor(enabled),
) {
    QuackSurface(
        modifier = Modifier.quackBorder(
            shape = RoundedCornerShape(8.dp),
            border = border,
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onClickFor(enabled, onClick),
        backgroundColor = backgroundColor,
    ) {
        QuackText(
            modifier = Modifier.padding(
                vertical = 7.dp,
                horizontal = 12.dp,
            ),
            text = text,
            typography = QuackTypography.Body1.change(
                color = textColor,
            ),
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
