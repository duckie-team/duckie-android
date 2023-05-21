/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun GrayBorderButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier,
        backgroundColor = QuackColor.White,
        border = QuackBorder(
            width = 1.dp,
            color = QuackColor.Gray3,
        ),
        shape = RoundedCornerShape(size = 100.dp),
        onClick = onClick,
    ) {
        QuackText(
            modifier = Modifier.padding(
                vertical = 4.dp,
                horizontal = 8.dp,
            ),
            text = text,
            style = QuackTextStyle.Body2.change(
                color = QuackColor.Gray1,
                textAlign = TextAlign.Center,
            ),
            singleLine = true,
        )
    }
}
