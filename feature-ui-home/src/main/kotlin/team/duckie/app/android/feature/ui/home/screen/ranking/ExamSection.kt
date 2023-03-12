/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.home.viewmodel.dummy.skeletonRankingItems
import team.duckie.app.android.shared.ui.compose.ColumnSpacer
import team.duckie.app.android.shared.ui.compose.DuckExamSmallCoverForColumn
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.shared.ui.compose.TextTabLayout
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.fastMapIndexed
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun ExamSection(
    tags: ImmutableList<Tag>,
) {
    val tagNames = remember(tags) { tags.fastMap { it.name } }
    val tagSelections = remember {
        mutableStateListOf(
            elements = Array(
                size = tags.size,
                init = { it == 0 },
            ),
        )
    }
    val textTabs = remember { persistentListOf("응시순", "오답률순") }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 10.dp),
    ) {
        // TODO:(EvergreenTree97) 태그의 inner padding이 바뀌어야 함
        QuackSingeLazyRowTag(
            items = tagNames,
            itemSelections = tagSelections.toImmutableList(),
            tagType = QuackTagType.Circle(),
            onClick = { index ->
                if (index == 0) { // "전체" 태그라면
                    if (tagSelections[index]) {
                        tagSelections.fastMap { false }
                    } else {
                        tagSelections.fastMapIndexed { mapIndex, _ -> mapIndex == 0 }
                    }
                } else {
                    tagSelections[index] = tagSelections[index].not()
                }
            },
            key = { _, name -> name }
        )
        ColumnSpacer(27.5.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackTitle2(text = "인기태그 랭킹")
            TextTabLayout(
                titles = textTabs,
                tabStyle = QuackTextStyle.Body2.change(color = QuackColor.Gray1),
                selectedTabStyle = QuackTextStyle.Body2.change(color = QuackColor.DuckieOrange),
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it },
                space = 16.dp,
            )
        }
        ColumnSpacer(17.5.dp)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = skeletonRankingItems) {
                RankingItem(duckTestCoverItem = it) {

                }
            }
        }
    }
}

@Composable
private fun RankingItem(
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
) {
    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
        DuckExamSmallCoverForColumn(
            duckTestCoverItem = duckTestCoverItem,
            onItemClick = onItemClick
        )
        RankingEdge(rank = 1)
    }
}

@Composable
private fun RankingEdge(rank: Int) {
    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = 24.dp,
                minHeight = 24.dp,
            )
            .background(
                brush = QuackColor.Black.toBrush(),
                alpha = 0.5f,
            ),
        contentAlignment = Alignment.Center,
    ) {
        QuackTitle2(
            modifier = Modifier.padding(
                horizontal = 9.dp,
                vertical = 2.dp,
            ),
            color = QuackColor.White,
            text = rank.toString(),
        )
    }
}
