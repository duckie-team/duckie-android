/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package team.duckie.app.android.common.compose.ui.dialog

import android.view.Gravity
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import team.duckie.app.android.common.compose.ui.DuckieDivider
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

enum class DuckieDialogPosition {
    BOTTOM, CENTER, TOP
}

private fun DialogWindowProvider.setGravity(position: DuckieDialogPosition) {
    val gravity = when (position) {
        DuckieDialogPosition.BOTTOM -> Gravity.BOTTOM
        DuckieDialogPosition.CENTER -> Gravity.CENTER
        DuckieDialogPosition.TOP -> Gravity.TOP
    }
    window.setGravity(gravity)
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
    dialogPosition: DuckieDialogPosition = DuckieDialogPosition.CENTER,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
    ),
) {
    if (visible) {
        Dialog(
            properties = properties,
            onDismissRequest = onDismissRequest,
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.setGravity(position = dialogPosition)

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
                            color = QuackColor.White.value,
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
                    DuckieDialogBottomLayout(
                        leftButtonText = leftButtonText,
                        leftButtonOnClick = leftButtonOnClick,
                        rightButtonText = rightButtonText,
                        rightButtonOnClick = rightButtonOnClick,
                    )
                }
            }
        }
    }
}

@Composable
fun DuckieDialog(
    modifier: Modifier = Modifier,
    container: @Composable () -> Unit,
    leftButtonText: String? = null,
    leftButtonOnClick: (() -> Unit)? = null,
    rightButtonText: String? = null,
    rightButtonOnClick: (() -> Unit)? = null,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    dialogPosition: DuckieDialogPosition = DuckieDialogPosition.CENTER,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
    ),
) {
    if (visible) {
        Dialog(
            properties = properties,
            onDismissRequest = onDismissRequest,
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.setGravity(position = dialogPosition)

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
                            color = QuackColor.White.value,
                        ),
                ) {
                    container()

                    DuckieDialogBottomLayout(
                        leftButtonText = leftButtonText,
                        leftButtonOnClick = leftButtonOnClick,
                        rightButtonText = rightButtonText,
                        rightButtonOnClick = rightButtonOnClick,
                    )
                }
            }
        }
    }
}

/**
 * [DuckieDialog] 내 최하단 Layout
 * 보통 0~2개의 버튼이 존재한다
 */
@Composable
private fun DuckieDialogBottomLayout(
    leftButtonText: String?,
    leftButtonOnClick: (() -> Unit)?,
    rightButtonText: String?,
    rightButtonOnClick: (() -> Unit)?,
) {
    DuckieDivider(modifier = Modifier.fillMaxWidth())

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
                        onClick = leftButtonOnClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                QuackText(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                    text = leftButtonText,
                    typography = QuackTypography.Subtitle.change(color = QuackColor.Black),
                    singleLine = true,
                )
            }
        }
        if (rightButtonText != null) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = QuackColor.DuckieOrange.value)
                    .quackClickable(
                        onClick = rightButtonOnClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                QuackText(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                    text = rightButtonText,
                    typography = QuackTypography.Subtitle.change(color = QuackColor.White),
                    singleLine = true,
                )
            }
        }
    }
}
