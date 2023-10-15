/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.sugar.QuackSubtitle

@Composable
fun TempSmallOutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(12.dp),
            )
            .clip(RoundedCornerShape(12.dp))
            .quackClickable(
                onClick = onClick,
            )
            .padding(
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackIcon(icon = icon)
        Spacer(space = 4.dp)
        QuackSubtitle(text = text)
    }
}
