/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection

/**
 * 주어진 [Constraints] 의 min 을 0 으로 설정하여 loose 하게 반환합니다.
 *
 * @param height mimHeight 을 loose 할지 여부
 * @param width minWidth 을 loose 할지 여부
 *
 * @return 주어진 조건에 맞게 loose 해진 [Constraints]
 */
fun Constraints.asLoose(
    height: Boolean = true,
    width: Boolean = false,
) = copy(
    minHeight = if (height) 0 else minHeight,
    minWidth = if (width) 0 else minWidth,
)

fun Constraints.centerHorizontally(width: Int, layoutDirection: LayoutDirection): Int =
    Alignment.CenterHorizontally.align(
        size = width,
        space = maxWidth,
        layoutDirection = layoutDirection,
    )

fun Constraints.centerVertical(size: Int): Int =
    Alignment.CenterVertically.align(
        size = size,
        space = maxHeight,
    )
