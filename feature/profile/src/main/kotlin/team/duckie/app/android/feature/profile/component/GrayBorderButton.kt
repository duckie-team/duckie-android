/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun GrayBorderButton(
    text: String,
    onClick: () -> Unit,
) {
    QuackText(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 100.dp))
            .quackClickable(onClick = onClick)
            .background(QuackColor.White.value)
            .border(
                width = 1.dp,
                brush = QuackColor.Gray3.toBrush(),
                shape = RoundedCornerShape(size = 100.dp),
            )
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp,
            ),
        text = text,
        typography = QuackTypography.Body2.change(
            color = QuackColor.Gray1,
            textAlign = TextAlign.Center,
        ),
        singleLine = true,
    )
}
