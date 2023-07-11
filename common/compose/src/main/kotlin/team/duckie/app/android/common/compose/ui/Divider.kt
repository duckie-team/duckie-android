/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor

@Composable
fun RowScope.Divider(
    modifier: Modifier = Modifier,
    height: Dp = 0.dp,
    width: Dp = 1.dp,
) {
    val heightModifier = if (height == 0.dp) {
        modifier
    } else {
        modifier.height(height)
    }
    Box(
        modifier = heightModifier
            .width(width)
            .background(QuackColor.Gray3.value),
    )
}
