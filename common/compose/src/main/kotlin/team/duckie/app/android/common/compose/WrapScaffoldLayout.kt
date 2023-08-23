/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("AnnotationOnSeparateLine")

package team.duckie.app.android.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.layout.SubcomposeLayout
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.fastMaxBy

private object WrapScaffoldLayoutId {
    const val TopBar = "TopContent"
    const val BodyContent = "Content"
    const val BottomBar = "BottomContent"
}

/**
 * Wrap(유연한) 사이즈를 지원하는 Custom Scaffold
 * [TopBar], [BodyContent], [BottomBar]로 구성된 [SubcomposeLayout]을 그립니다.
 */
@Composable
@UiComposable
fun WrapScaffoldLayout(
    modifier: Modifier = Modifier,
    fullScreen: Boolean = false,
    topBar: @Composable @UiComposable () -> Unit,
    content: @Composable @UiComposable () -> Unit,
    bottomBar: @Composable @UiComposable () -> Unit,
) = with(WrapScaffoldLayoutId) {
    SubcomposeLayout(
        modifier = modifier,
    ) { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceables = subcompose(TopBar, topBar).fastMap {
            it.measure(looseConstraints)
        }

        val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val bottomBarPlaceables = subcompose(BottomBar) {
            CompositionLocalProvider(
                content = bottomBar,
            )
        }.fastMap { it.measure(looseConstraints) }

        val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val bodyContentPlaceables = subcompose(BodyContent, content).fastMap {
            it.measure(looseConstraints.copy(maxHeight = layoutHeight - topBarHeight - bottomBarHeight))
        }

        val bodyContentHeight = bodyContentPlaceables.fastMaxBy { it.height }?.height ?: 0

        val totalHeight = topBarHeight + bodyContentHeight + bottomBarHeight

        layout(layoutWidth, if (fullScreen) layoutHeight else totalHeight) {
            bodyContentPlaceables.fastForEach {
                it.place(0, topBarHeight)
            }
            topBarPlaceables.fastForEach {
                it.place(0, 0)
            }
            bottomBarPlaceables.fastForEach {
                it.place(0, totalHeight - bottomBarHeight)
            }
        }
    }
}
