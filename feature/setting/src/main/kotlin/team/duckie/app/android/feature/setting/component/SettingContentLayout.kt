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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.setting.constans.SettingDesignToken
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun SettingContentLayout(
    title: String,
    content: String? = null,
    trailingText: String? = null,
    onTrailingTextClick: (() -> Unit)? = null,
    isBold: Boolean,
    onClick: (() -> Unit)? = null,
) = with(SettingDesignToken) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .quackClickable(
                rippleEnabled = false,
            ) {
                if (onClick != null) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (isBold) {
            QuackTitle2(
                text = title,
            )
        } else {
            QuackBody1(
                text = title,
            )
        }
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
