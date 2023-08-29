/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
fun QuackIconWrapper(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    icon: ImageVector,
    size: Dp = 24.dp,
    tint: QuackColor = QuackColor.Unspecified,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.quackClickable(
            rippleEnabled = false,
            onClick = onClick,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingContent?.invoke()
        QuackIcon(
            icon = icon,
            size = size,
            tint = tint,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )
        trailingContent?.invoke()
    }
}
