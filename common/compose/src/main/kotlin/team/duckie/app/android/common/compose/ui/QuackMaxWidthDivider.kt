/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor

/**
 * 스크린의 크기만큼 QuackDivider를 그립니다.
 * // TODO(riflockle7): 추후 사용처 확인해서 DuckieDivider 과 합칠 것
 */
@Composable
fun QuackMaxWidthDivider(
    modifier: Modifier = Modifier,
    color: QuackColor = QuackColor.Gray3,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = remember { configuration.screenWidthDp * density.density }

    DuckieDivider(
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = screenWidthPx.dp.roundToPx(),
                    ),
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            },
        color = color,
    )
}
