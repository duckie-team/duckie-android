/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable

@Preview
@Composable
fun PreviewExpandedCickable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
            )
            .padding(
                horizontal = 16.dp
            ),
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .size(50.dp)
        )
        Box(
            modifier = Modifier
            .background(QuackColor.Gray4.value)
            .size(50.dp)
            .expendedQuackClickable {
            }
        )
        Box(
            modifier = Modifier
            .background(QuackColor.Gray4.value)
            .size(50.dp)
            .expendedQuackClickable(
                verticalExpendedSize = 24.dp,
                horizontalExpendedSize = 24.dp,
            ) {
            }
        )
    }
}

fun Modifier.expendedQuackClickable(
    verticalExpendedSize: Dp = 12.dp,
    horizontalExpendedSize: Dp = 12.dp,
    rippleEnabled: Boolean = true,
    rippleColor: team.duckie.quackquack.ui.color.QuackColor? = null,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)?,
): Modifier {

    return this
        .layout { measurable, constraints ->
            val expansionVerticalPx = verticalExpendedSize.roundToPx()
            val expansionHorizontalPx = horizontalExpendedSize.roundToPx()

            val expandedConstraints = constraints.copy(
                minWidth = constraints.minWidth + expansionHorizontalPx,
                maxWidth = constraints.maxWidth + expansionHorizontalPx,
                minHeight = constraints.minHeight + expansionVerticalPx,
                maxHeight = constraints.maxHeight + expansionVerticalPx
            )
            val placeable = measurable.measure(expandedConstraints)

            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }
        .quackClickable(
            rippleEnabled = rippleEnabled,
            rippleColor = rippleColor,
            onLongClick = onLongClick,
            onClick = onClick,
        )
}
