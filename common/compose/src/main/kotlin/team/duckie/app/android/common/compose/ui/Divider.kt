/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor

@Composable
fun RowScope.Divider(
    modifier: Modifier = Modifier,
    height: Dp = 0.dp,
    width: Dp = 1.dp,
) {
    val heightModifier = if (height == 0.dp) {
        modifier
    } else {
        modifier.height(height)
    }
    Box(
        modifier = heightModifier
            .width(width)
            .background(QuackColor.Gray3.value),
    )
}

/**
 * 덕키에서 사용되는 구분선(divider)을 그립니다.
 *
 * @param modifier 이 컴포넌트에 사용할 [Modifier]
 */
@Composable
fun DuckieDivider(
    modifier: Modifier = Modifier,
    color: QuackColor = QuackColor.Gray3,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = color.value),
    )
}

/**
 * QuackDivider 를 그리기 위한 리소스들을 정의합니다.
 */
private object QuackDividerDefaults {
    val Color = QuackColor.Gray3
    val Height = 1.dp
}

/**
 * 덕키에서 사용되는 구분선(divider)을 그립니다.
 *
 * @param modifier 이 컴포넌트에 사용할 [Modifier]
 */
@Composable
public fun QuackDivider(modifier: Modifier = Modifier) {
    with(QuackDividerDefaults) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(Height)
                .background(color = Color.value),
        )
    }
}
