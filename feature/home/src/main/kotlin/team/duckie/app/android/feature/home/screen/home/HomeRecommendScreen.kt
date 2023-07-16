/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
)

package team.duckie.app.android.feature.home.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.DuckExamSmallCover
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.DuckieHorizontalPagerIndicator
import team.duckie.app.android.common.compose.ui.quack.todo.QuackAnnotatedText
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.common.kotlin.addHashTag
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.ExamType
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.HomeTopAppBar
import team.duckie.app.android.feature.home.constants.HomeStep
import team.duckie.app.android.feature.home.viewmodel.home.HomeState
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackLarge1
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType

private val HomeHorizontalPadding = PaddingValues(horizontal = 16.dp)

@Composable
internal fun HomeRecommendScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    homeViewModel: HomeViewModel = activityViewModel(),
    navigateToCreateProblem: () -> Unit,
    navigateToHomeDetail: (Int) -> Unit,
    navigateToSearch: (String) -> Unit,
    openExamBottomSheet: (Int) -> Unit,
) {
    val pageState = rememberPagerState()

    val lazyRecommendations = homeViewModel.recommendations.collectAsLazyPagingItems()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isHomeRecommendPullRefreshLoading,
        onRefresh = {
            homeViewModel.refreshRecommendations(forceLoading = true)
        },
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            item {
                HomeTopAppBar(
                    modifier = Modifier
                        .padding(HomeHorizontalPadding)
                        .padding(bottom = 16.dp),
                    selectedTabIndex = state.homeSelectedIndex.index,
                    onTabSelected = { step ->
                        homeViewModel.changedHomeScreen(HomeStep.toStep(step))
                    },
                    onClickedCreate = {
                        navigateToCreateProblem()
                    },
                )
            }

            item {
                HorizontalPager(
                    pageCount = state.jumbotrons.size,
                    state = pageState,
                ) { page ->
                    HomeRecommendJumbotronLayout(
                        modifier = Modifier
                            .padding(HomeHorizontalPadding),
                        recommendItem = state.jumbotrons[page],
                        onStartClicked = { examId ->
                            navigateToHomeDetail(examId)
                        },
                        isLoading = state.isHomeRecommendLoading,
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    DuckieHorizontalPagerIndicator(
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 60.dp),
                        pagerState = pageState,
                        pageCount = state.jumbotrons.size,
                        spacing = 6.dp,
                    )
                }
            }

            items(items = lazyRecommendations) { item ->
                HomeTopicRecommendLayout(
                    modifier = Modifier
                        .padding(bottom = 60.dp),
                    title = item?.title ?: "",
                    tag = item?.tag?.name ?: "",
                    exams = item?.exams?.toImmutableList() ?: persistentListOf(),
                    onExamClicked = { examId -> navigateToHomeDetail(examId) },
                    onTagClicked = { tag -> navigateToSearch(tag) },
                    isLoading = state.isHomeRecommendLoading,
                    onMoreClick = openExamBottomSheet,
                )
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isHomeRecommendPullRefreshLoading,
            state = pullRefreshState,
        )
    }
}

@Composable
private fun HomeRecommendJumbotronLayout(
    modifier: Modifier = Modifier,
    recommendItem: HomeState.HomeRecommendJumbotron,
    onStartClicked: (Int) -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = ThumbnailRatio)
                .clip(RoundedCornerShape(8.dp))
                .skeleton(isLoading),
            model = recommendItem.coverUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Spacer(modifier = Modifier.height(24.dp))
        QuackLarge1(
            modifier = Modifier.skeleton(isLoading),
            text = recommendItem.title,
        )
        Spacer(modifier = Modifier.height(12.dp))
        QuackBody1(
            modifier = Modifier.skeleton(isLoading),
            text = recommendItem.content,
            align = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        QuackLargeButton(
            modifier = Modifier.skeleton(isLoading),
            type = QuackLargeButtonType.Fill,
            text = recommendItem.buttonContent,
            onClick = { onStartClicked(recommendItem.examId) },
            enabled = true,
        )
        Spacer(modifier = Modifier.height(8.dp))

        when (recommendItem.type) {
            ExamType.Text -> {}
            ExamType.Audio -> QuackBody3(
                modifier = Modifier.skeleton(isLoading),
                text = stringResource(id = R.string.home_audio_volume_control_message),
            )

            ExamType.Video -> QuackBody3(
                modifier = Modifier.skeleton(isLoading),
                text = stringResource(id = R.string.home_video_volume_control_message),
            )

            else -> {}
        }
    }
}

@Composable
private fun HomeTopicRecommendLayout(
    modifier: Modifier = Modifier,
    title: String,
    tag: String,
    exams: ImmutableList<Exam>,
    onExamClicked: (Int) -> Unit,
    onTagClicked: (String) -> Unit,
    onMoreClick: (Int) -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = modifier,
    ) {
        // TODO(limsaehyun): QuackAnnotatedHeadLine2로 교체 필요
        // https://github.com/duckie-team/quack-quack-android/issues/442
        QuackAnnotatedText(
            modifier = Modifier
                .padding(HomeHorizontalPadding)
                .skeleton(isLoading),
            text = title,
            highlightTextPairs = persistentListOf(
                tag.addHashTag() to { onTagClicked(tag) },
            ),
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        ) {
            items(items = exams) { item ->
                DuckExamSmallCover(
                    duckTestCoverItem = DuckTestCoverItem(
                        testId = item.id,
                        thumbnailUrl = item.thumbnailUrl,
                        nickname = item.user?.nickname ?: "",
                        title = item.title,
                        solvedCount = item.solvedCount ?: 0,
                        heartCount = item.heartCount ?: 0,
                    ),
                    onItemClick = {
                        onExamClicked(item.id)
                    },
                    onMoreClick = {
                        onMoreClick(item.id)
                    },
                    isLoading = isLoading,
                )
            }
        }
    }
}
