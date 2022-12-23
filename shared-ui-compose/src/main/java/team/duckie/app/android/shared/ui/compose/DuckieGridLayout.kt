/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.app.android.util.kotlin.fastForEachIndexed

@Composable
fun <T> DuckieGridLayout(
    modifier: Modifier = Modifier,
    columns: Int = 3,
    verticalPadding: Dp = 12.dp,
    items: ImmutableList<T>,
    content: @Composable (
        index: Int,
        item: T,
    ) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = verticalPadding),
    ) {
        items.chunked(columns)
            .fastForEachIndexed { index, chunkedList ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    chunkedList.fastForEachIndexed { chunkedIndex, item ->
                        Box(
                            modifier = Modifier.weight(1f),
                        ) {
                            content(chunkedIndex + columns * index, item)
                        }
                    }
                    if (chunkedList.size < columns) {
                        val spacerCount = columns - chunkedList.size
                        repeat(spacerCount) {
                            Spacer(modifier = Modifier.weight(1f / (spacerCount)))
                        }
                    }
                }
            }
    }
}
