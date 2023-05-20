package team.duckie.app.android.feature.solve.problem.screen

import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe

internal fun screenMeasurePolicy(
    topLayoutId: String,
    contentLayoutId: String,
    bottomLayoutId: String,
) = MeasurePolicy { measurableItems, constraints ->
    val looseConstraints = constraints.asLoose()

    val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
        measureItem.layoutId == topLayoutId
    }?.measure(looseConstraints) ?: npe()
    val topAppBarHeight = topAppBarMeasurable.height

    val bottomBarMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == bottomLayoutId
    }?.measure(looseConstraints) ?: npe()
    val bottomBarHeight = bottomBarMeasurable.height

    val contentHeight = constraints.maxHeight - topAppBarHeight - bottomBarHeight
    val contentConstraints = constraints.copy(
        minHeight = contentHeight,
        maxHeight = contentHeight,
    )
    val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == contentLayoutId
    }?.measure(contentConstraints) ?: npe()

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
