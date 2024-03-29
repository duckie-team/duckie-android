/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen.ranking

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.common.compose.itemsPagingKey
import team.duckie.app.android.common.compose.ui.DuckExamSmallCoverForColumn
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.TextTabLayout
import team.duckie.app.android.common.compose.ui.quack.todo.QuackOutLinedSingeLazyRowTag
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.material.QuackColor as QuackV2Color

@Composable
internal fun ExamSection(
    viewModel: RankingViewModel,
    lazyGridState: LazyGridState,
    openReportDialog: (Int) -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val tagNames = remember(state.examTags) {
        state.examTags.fastMap { it.name }
    }
    val textTabs = remember {
        persistentListOf(
            context.getString(R.string.solve_order),
            context.getString(R.string.wrong_answer_rate_order),
        )
    }
    val examRankings = viewModel.examRankings.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 10.dp),
    ) {
        // TODO:(EvergreenTree97) 태그의 inner padding이 바뀌어야 함
        QuackOutLinedSingeLazyRowTag(
            modifier = Modifier
                .fillMaxWidth()
                .skeleton(state.isTagLoading),
            items = tagNames,
            itemSelections = state.tagSelections,
            trailingIcon = null,
            onClick = viewModel::changeSelectedTags,
        )
        Spacer(space = 28.dp)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            state = lazyGridState,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(
                span = { HeaderSpan() },
            ) {
                RankingHeader(
                    titles = textTabs,
                    selectedTabIndex = state.selectedExamOrder,
                    onTabSelected = viewModel::setSelectedExamOrder,
                )
            }
            items(
                count = examRankings.itemCount,
                key = itemsPagingKey(
                    items = examRankings,
                    key = { examRankings[it]?.id },
                ),
            ) { index ->
                examRankings[index]?.let { item ->
                    RankingItem(
                        modifier = Modifier.padding(bottom = 48.dp),
                        duckTestCoverItem = DuckTestCoverItem(
                            testId = item.id,
                            thumbnailUrl = item.thumbnailUrl,
                            nickname = item.user?.nickname ?: "",
                            title = item.title,
                            solvedCount = item.solvedCount ?: 0,
                            heartCount = item.heartCount ?: 0,
                        ),
                        onItemClick = viewModel::clickExam,
                        rank = index + 1,
                        isLoading = examRankings.loadState.refresh == LoadState.Loading || state.isPagingDataLoading,
                        onMoreClick = openReportDialog,
                    )
                }
            }
        }
    }
}

@Stable
private val HeaderSpan = { GridItemSpan(2) }

@Composable
private fun RankingHeader(
    titles: ImmutableList<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackTitle2(text = stringResource(id = R.string.popular_tag_ranking))
        TextTabLayout(
            titles = titles,
            tabStyle = QuackTypography.Body2.change(color = QuackV2Color.Gray1),
            selectedTabStyle = QuackTypography.Body2.change(color = QuackV2Color.DuckieOrange),
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            space = 16.dp,
        )
    }
}

@Composable
private fun RankingItem(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: (Int) -> Unit,
    rank: Int,
    isLoading: Boolean,
    onMoreClick: (Int) -> Unit,
) {
    Box(modifier = modifier.clip(RoundedCornerShape(8.dp))) {
        DuckExamSmallCoverForColumn(
            duckTestCoverItem = duckTestCoverItem,
            onItemClick = { onItemClick(duckTestCoverItem.testId) },
            isLoading = isLoading,
            onMoreClick = {
                onMoreClick(duckTestCoverItem.testId)
            },
        )
        RankingEdge(rank = rank)
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
        QuackText(
            modifier = Modifier.padding(
                horizontal = 9.dp,
                vertical = 2.dp,
            ),
            text = rank.toString(),
            typography = QuackTypography.Title2.change(color = QuackColor.White),
        )
    }
}
