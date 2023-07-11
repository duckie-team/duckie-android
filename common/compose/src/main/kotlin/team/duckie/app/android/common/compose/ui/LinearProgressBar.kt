/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor

@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 4.dp,
    progressColor: QuackColor = QuackColor.DuckieOrange,
    backgroundColor: QuackColor = QuackColor.Gray3,
) {
    Canvas(
        modifier = modifier
            .clip(CircleShape)
            .height(strokeWidth)
            .fillMaxWidth(),
    ) {
        val strokeSize = size.height
        drawLinearIndicatorBackground(
            color = backgroundColor,
            strokeWidth = strokeSize,
        )
        drawLinearIndicator(
            startFraction = 0f,
            endFraction = progress,
            color = progressColor,
            strokeWidth = strokeSize,
        )
    }
}

private fun DrawScope.drawLinearIndicatorBackground(
    color: QuackColor,
    strokeWidth: Float,
) = drawLinearIndicator(
    startFraction = 0f,
    endFraction = 1f,
    color = color,
    strokeWidth = strokeWidth,
)

private fun DrawScope.drawLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: QuackColor,
    strokeWidth: Float,
) {
    val width = size.width
    val height = size.height
    val yOffset = height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    drawLine(
        color = color.composeColor,
        start = Offset(barStart, yOffset),
        end = Offset(barEnd, yOffset),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round,
    )
}
