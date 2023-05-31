/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
fun BackPressedTopAppBar(
    onBackPressed: () -> Unit,
    trailingSlot: @Composable (RowScope.() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        QuackImage(
            src = QuackIcon.ArrowBack,
            onClick = onBackPressed,
        )
        if (trailingSlot != null) trailingSlot()
    }
}


@Composable
fun BackPressedHeadLineTopAppBar(
    title: String,
    isLoading: Boolean,
    trailingIcon: QuackIcon? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onBackPressed: () -> Unit,
) {
    BackPressedTopAppBar(onBackPressed = onBackPressed) {
        QuackHeadLine1(
            modifier = Modifier.skeleton(isLoading),
            text = title,
        )
        Spacer(weight = 1f)
        if (trailingIcon != null) {
            QuackImage(
                size = DpSize(24.dp, 24.dp),
                src = trailingIcon,
                onClick = {
                    if (onTrailingIconClick != null) onTrailingIconClick()
                },
            )
        }
    }
}

@Composable
fun BackPressedHeadLine2TopAppBar(
    title: String,
    isLoading: Boolean = false,
    onBackPressed: () -> Unit,
) {
    BackPressedTopAppBar(onBackPressed = onBackPressed) {
        QuackHeadLine2(
            modifier = Modifier.skeleton(isLoading),
            text = title,
        )
    }
}
