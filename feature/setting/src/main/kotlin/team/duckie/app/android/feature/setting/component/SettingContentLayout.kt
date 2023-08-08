/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.setting.constans.SettingDesignToken
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun SettingContentLayout(
    modifier: Modifier = Modifier,
    title: String,
    content: String? = null,
    trailingText: String? = null,
    onTrailingTextClick: (() -> Unit)? = null,
    typography: QuackTypography = QuackTypography.Title2,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    onClick: (() -> Unit)? = null,
) = with(SettingDesignToken) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .quackClickable(
                rippleEnabled = false,
                onClick = {
                    if (onClick != null) {
                        onClick()
                    }
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        QuackText(
            text = title,
            typography = typography,
        )
        if (content != null) {
            QuackText(
                modifier = Modifier.padding(start = 12.dp),
                text = content,
                typography = SettingHorizontalResultTypography,
            )
        }
        if (trailingText != null) {
            QuackText(
                modifier = Modifier
                    .quackClickable(rippleEnabled = false, onClick = onTrailingTextClick)
                    .padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                text = trailingText,
                typography = SettingHorizontalResultTypography,
            )
        }
    }
}
