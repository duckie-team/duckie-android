/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.viewall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.common.compose.ui.DuckExamSmallCoverForColumn
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.Spacer

@Composable
fun ViewAllScreen(
    title: String,
    onBackPressed: () -> Unit,
    duckTestCoverItems: ImmutableList<DuckTestCoverItem>,
    onItemClick: (DuckTestCoverItem) -> Unit,
    onMoreClick: (() -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BackPressedHeadLine2TopAppBar(
            title = title,
            onBackPressed = onBackPressed,
        )
        Spacer(space = 20.dp)
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(duckTestCoverItems) { duckTestCoverItem ->
                DuckExamSmallCoverForColumn(
                    duckTestCoverItem = duckTestCoverItem,
                    onItemClick = { onItemClick(duckTestCoverItem) },
                    isLoading = false,
                    onMoreClick = onMoreClick, // TODO(EvergreenTree97) 추후 신고하기 구현 필요
                )
            }
        }
    }
}
