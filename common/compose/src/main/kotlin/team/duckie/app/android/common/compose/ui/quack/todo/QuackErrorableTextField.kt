/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

sealed class QuackErrorableTextFieldState {
    object Normal : QuackErrorableTextFieldState()
    class Success(val successText: String) : QuackErrorableTextFieldState()
    class Error(val errorText: String) : QuackErrorableTextFieldState()
}

@Composable
fun QuackErrorableTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    maxLength: Int,
    textFieldState: QuackErrorableTextFieldState,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    Column(modifier = modifier) {
        BasicTextField(
            modifier = modifier,
            value = text,
            onValueChange = onTextChanged,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
            ),
            keyboardActions = keyboardActions,
            textStyle = QuackTypography.HeadLine2.asComposeStyle(),
            singleLine = true,
        ) { innerTextField ->
            QuackTextFieldDecorationBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .bottomBorder(
                        strokeWidth = 1.dp,
                        textFieldState = textFieldState,
                    ),
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp)
                    ) {
                        if (text.isEmpty()) {
                            QuackText(
                                text = placeholderText,
                                typography = QuackTypography.HeadLine2.change(
                                    color = QuackColor.Gray2,
                                ),
                            )
                        }
                        innerTextField()
                    }
                },
                trailingContent = {
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                    ) {
                        QuackText(
                            text = "${text.length}",
                            typography = QuackTypography.Subtitle.change(
                                color = if (text.isEmpty()) {
                                    QuackColor.Gray2
                                } else {
                                    QuackColor.Black
                                },
                            )
                        )
                        QuackText(
                            text = "/",
                            typography = QuackTypography.Subtitle.change(
                                color = QuackColor.Gray2,
                            ),
                        )
                        QuackText(
                            text = "$maxLength",
                            typography = QuackTypography.Subtitle.change(
                                color = QuackColor.Gray2,
                            ),
                        )
                    }
                },
            )
        }
        Crossfade(
            modifier = Modifier.padding(top = 4.dp),
            targetState = textFieldState,
            label = "",
        ) { state ->
            when (state) {
                is QuackErrorableTextFieldState.Error -> {
                    QuackText(
                        text = state.errorText,
                        typography = QuackTypography.Body1.change(
                            color = QuackColor.Alert,
                        ),
                    )
                }

                is QuackErrorableTextFieldState.Success -> {
                    QuackText(
                        text = state.successText,
                        typography = QuackTypography.Body1.change(
                            color = QuackColor.Success,
                        ),
                    )
                }

                QuackErrorableTextFieldState.Normal -> {}
            }
        }
    }
}

@Composable
private fun QuackTextFieldDecorationBox(
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        leadingContent?.invoke() ?: Spacer(modifier = Modifier.weight(1f))
        trailingContent?.invoke() ?: Spacer(modifier = Modifier.weight(1f))
    }
}

private fun Modifier.bottomBorder(
    strokeWidth: Dp,
    textFieldState: QuackErrorableTextFieldState,
) = composed(
    factory = {
        val color by animateColorAsState(
            targetValue = when (textFieldState) {
                is QuackErrorableTextFieldState.Error -> {
                    QuackColor.Alert.value
                }

                QuackErrorableTextFieldState.Normal -> {
                    QuackColor.Gray2.value
                }

                else -> QuackColor.Success.value
            }, label = ""
        )
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

