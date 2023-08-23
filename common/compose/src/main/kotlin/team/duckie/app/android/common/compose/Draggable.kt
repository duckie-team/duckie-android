/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.fastMaxBy
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Flag
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import kotlin.math.roundToInt

private object DraggableSpec {
    const val ANIMATION_DURATION = 500
    const val MIN_DRAG_AMOUNT = 6
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableContent(
    modifier: Modifier = Modifier,
    cardOffset: Float,
    isRevealed: Boolean,
    onRevealedChanged: (Boolean) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) = with(DraggableSpec) {

    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset else 0f },
    )

    content(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount >= MIN_DRAG_AMOUNT -> onRevealedChanged(false)
                        dragAmount < -MIN_DRAG_AMOUNT -> onRevealedChanged(true)
                    }
                }
            },
    )
}

@Composable
fun DraggableBox(
    modifier: Modifier = Modifier,
    isRevealed: Boolean,
    onRevealedChanged: (Boolean) -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
    backgroundContent: @Composable (modifier: Modifier) -> Unit,
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val tempContentHeight =
            subcompose("tempContent") { content(Modifier) } // for get content height fast
                .fastMap { it.measure(constraints) }.fastMaxBy { it.height }?.height ?: 0

        val backgroundPlaceable = subcompose("background") {
            backgroundContent(Modifier.height(tempContentHeight.toDp()))
        }.fastMap { it.measure(constraints) }

        val backgroundWidth = backgroundPlaceable.fastMaxBy { it.width }?.width ?: 0

        val contentPlaceable = subcompose("content") {
            DraggableContent(
                cardOffset = -backgroundWidth.toDp().toPx(),
                isRevealed = isRevealed,
                onRevealedChanged = onRevealedChanged,
            ) { modifier ->
                content(modifier)
            }
        }.fastMap { it.measure(constraints) }

        val contentHeight = contentPlaceable.fastMaxBy { it.height }?.height ?: 0

        layout(constraints.maxWidth, contentHeight) {
            backgroundPlaceable.fastForEach {
                it.place(
                    x = constraints.maxWidth - backgroundWidth,
                    y = 0,
                )
            }
            contentPlaceable.fastForEach {
                it.place(0, 0)
            }
        }
    }
}

@Composable
fun PullToDeleteButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(52.dp)
            .background(QuackColor.Gray4.value)
            .quackClickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        QuackIcon(
            icon = icon,
            tint = QuackColor.Alert,
        )
    }
}

@Preview
@Composable
fun PreviewDraggableBox() {
    var isRevealed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        DraggableBox(
            isRevealed = isRevealed,
            onRevealedChanged = { isRevealed = it },
            backgroundContent = {
                Row {
                    PullToDeleteButton(
                        modifier = Modifier.fillMaxHeight(),
                        icon = QuackIcon.Outlined.Flag,
                        onClick = { isRevealed = false },
                    )
                    PullToDeleteButton(
                        icon = QuackIcon.Outlined.Flag,
                        onClick = { isRevealed = false },
                    )
                }
            },
            content = { modifier ->
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = "hello")
                }
            },
        )
    }
}
