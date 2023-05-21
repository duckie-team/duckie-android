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
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
internal fun SettingContentLayout(
    title: String,
    content: String? = null,
    trailingText: String? = null,
    isBold: Boolean,
    onClick: (() -> Unit)? = null,
) {
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
                color = QuackColor.Black,
            )
        } else {
            QuackBody1(
                text = title,
                color = QuackColor.Black,
            )
        }
        if (content != null) {
            QuackBody1(
                modifier = Modifier.padding(start = 12.dp),
                text = content,
                color = QuackColor.Gray1,
            )
        }
        if (trailingText != null) {
            QuackBody1(
                text = trailingText,
                color = QuackColor.Gray1,
            )
        }
    }
}
