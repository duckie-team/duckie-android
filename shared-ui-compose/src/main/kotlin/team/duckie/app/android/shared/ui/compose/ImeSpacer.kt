/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

/**
 * 실시간 IME 높이에 맞게 Spacer 의 높이를 조정합니다.
 * IME 작업을 수행하기 위해선 아래와 같은 사용 설정이 되어 있어야 합니다.
 *
 * 1. BaseActivity 를 상속한 Owner Activity
 * 2. `android:windowSoftInputMode="adjustResize"`
 */
@Composable
fun ImeSpacer() {
    val imeInsets = WindowInsets.ime
    val navigationBarInsets = WindowInsets.navigationBars

    Spacer(
        modifier = Modifier.layout { measurable, _constraints ->
            val imeHeight = imeInsets.getBottom(this)
            val nagivationBarHeight = navigationBarInsets.getBottom(this)
            // ime height 에 navigation height 가 포함되는 것으로 추측됨
            val height = imeHeight
                .minus(nagivationBarHeight)
                .coerceAtLeast(0)

            val constraints = _constraints.copy(
                minHeight = height,
                maxHeight = height,
            )
            val placeable = measurable.measure(constraints)

            layout(
                width = constraints.maxWidth,
                height = height,
            ) {
                placeable.place(x = 0, y = 0)
            }
        },
    )
}
