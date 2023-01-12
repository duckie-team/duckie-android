/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.util.kotlin.fastForEachIndexed

@Composable
fun <T> DuckieGridLayout(
    modifier: Modifier = Modifier,
    columns: Int = 3,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 0.dp,
    items: ImmutableList<T>,
    content: @Composable (index: Int, item: T) -> Unit,
) {
    require(columns >= 2) {
        stringResource(id = R.string.require_columns)
    }
    val chunkedItems = remember(items, columns) { items.chunked(columns) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = verticalPadding),
    ) {
        chunkedItems.fastForEachIndexed { index, chunkedList ->
            val chuckedSize = remember(chunkedList) { chunkedList.size }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                chunkedList.fastForEachIndexed { chunkedIndex, item ->
                    val elementIndex = chunkedIndex + columns * index
                    val elementModifier = if (elementIndex == columns - 1) {
                        Modifier.weight(1f)
                    } else {
                        Modifier
                            .weight(1f)
                            .padding(end = horizontalPadding)
                    }
                    Box(
                        modifier = elementModifier,
                    ) {
                        content(elementIndex, item)
                    }
                }
                if (chuckedSize < columns) {
                    val spacerCount = columns - chuckedSize
                    repeat(spacerCount) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f / spacerCount)
                                .padding(horizontal = horizontalPadding)
                        )
                    }
                }
            }
        }
    }
}
