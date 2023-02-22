/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun StaggeredLayout(
    modifier: Modifier = Modifier,
    horizontalSpace: Dp = 4.dp,
    verticalSpace: Dp = 4.dp,
    content: @Composable () -> Unit,
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var y = 0
        var x = 0
        var maxY = 0
        val flowContents = mutableListOf<FlowContent>()

        val verticalSpacePx = horizontalSpace.roundToPx()
        val horizontalSpacePx = verticalSpace.roundToPx()

        placeables.forEach { placeable ->
            if (placeable.width + x > constraints.maxWidth) {
                x = 0
                y += maxY
                maxY = 0
            }
            maxY = max(placeable.height + verticalSpacePx, maxY)

            flowContents.add(FlowContent(placeable, x, y))
            x += placeable.width + horizontalSpacePx
        }
        y += maxY

        layout(constraints.maxWidth, y) {
            flowContents.forEach {
                it.placeable.place(it.x, it.y)
            }
        }
    }
}

@Immutable
private data class FlowContent(
    val placeable: Placeable,
    val x: Int,
    val y: Int,
)
