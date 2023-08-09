/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowBackId
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

@Composable
fun BackPressedTopAppBar(
    color: Color = Color.Transparent,
    onBackPressed: () -> Unit,
    trailingSlot: @Composable (RowScope.() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        QuackImage(
            modifier = Modifier.quackClickable(onClick = onBackPressed),
            src = QuackIcon.ArrowBackId,
        )
        if (trailingSlot != null) trailingSlot()
    }
}

@Composable
fun BackPressedHeadLineTopAppBar(
    title: String,
    isLoading: Boolean = false,
    trailingContent: (@Composable () -> Unit)? = null,
    onBackPressed: () -> Unit,
) {
    BackPressedTopAppBar(onBackPressed = onBackPressed) {
        QuackHeadLine1(
            modifier = Modifier.skeleton(isLoading),
            text = title,
        )
        Spacer(weight = 1f)
        if (trailingContent != null) {
            trailingContent()
        }
    }
}

@Composable
fun BackPressedHeadLine2TopAppBar(
    title: String,
    isLoading: Boolean = false,
    trailingContent: (@Composable () -> Unit)? = null,
    onBackPressed: () -> Unit,
) {
    BackPressedTopAppBar(onBackPressed = onBackPressed) {
        QuackHeadLine2(
            modifier = Modifier.skeleton(isLoading),
            text = title,
        )
        Spacer(weight = 1f)
        if (trailingContent != null) {
            trailingContent()
        }
    }
}
