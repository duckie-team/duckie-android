/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import team.duckie.quackquack.ui.component.QuackSelectableImageType

@Composable
fun QuackSelectableImage(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    src: Any?,
    size: DpSize? = null,
    shape: Shape = RectangleShape,
    isCheckOverlay: Boolean = false,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    contentDescription: String? = null,
) {
    team.duckie.quackquack.ui.component.QuackSelectableImage(
        modifier = modifier,
        isSelected = isSelected,
        src = src,
        size = size,
        tint = null,
        shape = shape,
        selectableType = if (isCheckOverlay) QuackSelectableImageType.CheckOverlay else QuackSelectableImageType.TopEndCheckBox,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
        contentScale = contentScale,
        contentDescription = contentDescription,
    )
}
