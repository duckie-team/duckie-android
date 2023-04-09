/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package team.duckie.app.android.shared.ui.compose.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import team.duckie.app.android.util.kotlin.runIf
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.modifier.quackClickable

enum class DuckieDialogPosition {
    BOTTOM, CENTER, TOP
}

fun Modifier.duckieDialogPosition(pos: DuckieDialogPosition) = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints)
    layout(constraints.maxWidth, constraints.maxHeight) {
        when (pos) {
            DuckieDialogPosition.BOTTOM -> {
                placeable.place(0, constraints.maxHeight - placeable.height)
            }

            DuckieDialogPosition.CENTER -> {
                placeable.place(
                    x = 0,
                    y = Alignment.CenterVertically.align(
                        size = placeable.height,
                        space = constraints.maxHeight,
                    ),
                )
            }

            DuckieDialogPosition.TOP -> {
                placeable.place(0, 0)
            }
        }
    }
}

@Composable
fun DuckieDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String? = null,
    leftButtonText: String? = null,
    leftButtonOnClick: (() -> Unit)? = null,
    rightButtonText: String? = null,
    rightButtonOnClick: (() -> Unit)? = null,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
    ),
) {
    if (visible) {
        Dialog(
            properties = properties,
            onDismissRequest = onDismissRequest,
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = QuackColor.White.composeColor,
                        ),
                ) {
                    QuackHeadLine2(
                        modifier = Modifier
                            .padding(
                                top = 28.dp,
                                start = 28.dp,
                                end = 28.dp,
                            ).runIf(message == null) {
                                padding(bottom = 28.dp)
                            },
                        text = title,
                    )
                    if (message != null) {
                        QuackBody2(
                            modifier = Modifier
                                .padding(
                                    top = 12.dp,
                                    start = 28.dp,
                                    end = 28.dp,
                                    bottom = 28.dp,
                                ),
                            text = message,
                        )
                    }
                    QuackDivider(modifier = Modifier.fillMaxWidth())
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (leftButtonText != null) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .quackClickable(
                                        rippleEnabled = false,
                                        onClick = leftButtonOnClick,
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                QuackSubtitle(
                                    modifier = Modifier
                                        .quackClickable(
                                            rippleEnabled = false,
                                            onClick = leftButtonOnClick,
                                        )
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp,
                                        ),
                                    text = leftButtonText,
                                    color = QuackColor.Black,
                                )
                            }
                        }
                        if (rightButtonText != null) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(color = QuackColor.DuckieOrange.composeColor)
                                    .quackClickable(
                                        rippleEnabled = false,
                                        onClick = leftButtonOnClick,
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                QuackSubtitle(
                                    modifier = Modifier
                                        .quackClickable(
                                            rippleEnabled = false,
                                            onClick = rightButtonOnClick,
                                        )
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp,
                                        ),
                                    text = rightButtonText,
                                    color = QuackColor.White,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}