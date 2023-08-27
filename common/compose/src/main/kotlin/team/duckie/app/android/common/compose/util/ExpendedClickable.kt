/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackClickable

fun Modifier.expendedQuackClickable(
    verticalExpendedSize: Dp = 12.dp,
    horizontalExpendedSize: Dp = 12.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor? = null,
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
                maxHeight = constraints.maxHeight + expansionVerticalPx,
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
