/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.profile.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun FollowSection(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    EditButton(
        modifier = Modifier.fillMaxWidth(),
        text = if (enabled) {
            stringResource(id = R.string.follow)
        } else {
            stringResource(id = R.string.following)
        },
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = false,
    plainTextColor: QuackColor = QuackColor.Gray1,
) {
    QuackText(
        modifier = modifier
            .clip(RoundedCornerShape(size = 8.dp))
            .quackClickable(onClick = onClick)
            .background(
                if (enabled) {
                    QuackColor.White.toBrush()
                } else {
                    QuackColor.Gray4.toBrush()
                },
            )
            .border(
                width = 1.dp,
                brush = if (enabled) {
                    QuackColor.DuckieOrange.toBrush()
                } else {
                    QuackColor.Unspecified.toBrush()
                },
                shape = RoundedCornerShape(size = 8.dp),
            )
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        text = text,
        typography = QuackTypography.Body1.change(
            color = if (enabled) {
                QuackColor.DuckieOrange
            } else {
                plainTextColor
            },
            textAlign = TextAlign.Center,
        ),
        singleLine = true,
    )
}
