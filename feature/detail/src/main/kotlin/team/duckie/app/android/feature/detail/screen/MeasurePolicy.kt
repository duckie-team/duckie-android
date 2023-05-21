/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.screen

import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.npe

internal const val DetailScreenTopAppBarLayoutId = "DetailScreenTopAppBar"
internal const val DetailScreenContentLayoutId = "DetailScreenContent"
internal const val DetailScreenBottomBarLayoutId = "DetailScreenBottomBar"

internal fun screenMeasurePolicy(
    topLayoutId: String,
    contentLayoutId: String,
    bottomLayoutId: String,
) = MeasurePolicy { measurableItems, constraints ->
    // 1. topAppBar, bottomBar 높이값 측정
    val looseConstraints = constraints.asLoose()

    val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
        measureItem.layoutId == topLayoutId
    }?.measure(looseConstraints) ?: npe()
    val topAppBarHeight = topAppBarMeasurable.height

    val bottomBarMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == bottomLayoutId
    }?.measure(looseConstraints) ?: npe()
    val bottomBarHeight = bottomBarMeasurable.height

    // 2. content 제약 설정 및 content 높이값 측정
    val contentHeight = constraints.maxHeight - topAppBarHeight - bottomBarHeight
    val contentConstraints = constraints.copy(
        minHeight = contentHeight,
        maxHeight = contentHeight,
    )
    val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == contentLayoutId
    }?.measure(contentConstraints) ?: npe()

    // 3. 위에서 추출한 값들을 활용해 레이아웃 위치 처리
    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarMeasurable.place(
            x = 0,
            y = 0,
        )
        contentMeasurable.place(
            x = 0,
            y = topAppBarHeight,
        )
        bottomBarMeasurable.place(
            x = 0,
            y = topAppBarHeight + contentHeight,
        )
    }
}
