/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> QuackGridLayout(
    modifier: Modifier = Modifier,
    items: ImmutableList<T>,
    state: LazyGridState = rememberLazyGridState(),
    verticalSpacing: Dp = 3.dp,
    horizontalSpacing: Dp = 3.dp,
    key: ((index: Int, item: T) -> Any)? = null,
    span: (LazyGridItemSpanScope.(index: Int, item: T) -> GridItemSpan)? = null,
    contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    team.duckie.quackquack.ui.component.QuackGridLayout(
        modifier = modifier,
        items = items,
        state = state,
        verticalSpacing = verticalSpacing,
        horizontalSpacing = horizontalSpacing,
        key = key,
        span = span,
        contentType = contentType,
        itemContent = itemContent,
    )

}
