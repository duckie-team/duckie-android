/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.home.screen.music

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import team.duckie.app.android.common.compose.isLastPage
import team.duckie.app.android.common.compose.scrollToOriginalPage
import team.duckie.app.android.common.compose.ui.DuckExamSmallCover
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.columnSpacer
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.viewmodel.music.MusicSideEffect
import team.duckie.app.android.feature.home.viewmodel.music.MusicViewModel
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Notice
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackLarge1

private const val PullRefreshDelay = 1000L
private const val HeroModuleSwipeInterval = 3000L
private const val CollapsingToolbarExpandSpeed = 100

@OptIn(ExperimentalToolbarApi::class)
@Composable
internal fun MusicScreen(
    vm: MusicViewModel,
) {
    val state = vm.collectAsState().value
    var isPullRefresh by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val heroModulePagerState = rememberPagerState {
        5
    }
    val lazyListState = rememberLazyListState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    vm.collectSideEffect {
        when (it) {
            is MusicSideEffect.ListPullUp -> {
                lazyListState.animateScrollToItem(0)
                toolbarScaffoldState.toolbarState.expand(CollapsingToolbarExpandSpeed)
            }
        }
    }

    heroModulePagerState.scrollToOriginalPage(rememberPage = state.heroModulePage)

    LaunchedEffect(key1 = heroModulePagerState.currentPage) {
        delay(HeroModuleSwipeInterval)
        withContext(NonCancellable) {
            if (heroModulePagerState.isLastPage) {
                heroModulePagerState.animateScrollToPage(
                    page = 0.also(vm::saveHeroModulePage),
                )
            } else {
                heroModulePagerState.animateScrollToPage(
                    page = (heroModulePagerState.currentPage + 1).also(vm::saveHeroModulePage),
                )
            }
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isPullRefresh,
        onRefresh = {
            isPullRefresh = true
            coroutineScope.launch {
                delay(PullRefreshDelay)
                isPullRefresh = false
            }
        },
    )

    CollapsingToolbarScaffold(
        modifier = Modifier.pullRefresh(pullRefreshState),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .pin(),
                contentAlignment = Alignment.TopEnd,
            ) {
                QuackIcon(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(end = 16.dp),
                    icon = QuackIcon.Outlined.Notice,
                )
            }
            QuackLarge1(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
                    .road(
                        whenCollapsed = Alignment.TopStart,
                        whenExpanded = Alignment.BottomStart,
                    ),
                text = stringResource(id = R.string.music_test),
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            columnSpacer(8.dp)
            item {
                HeroModule(
                    pagerState = heroModulePagerState,
                    items = (1..5).map {
                        "https://images.unsplash.com/photo-1693418161641-99928097b5ff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1760&q=80"
                    }.toImmutableList(),
                    onClickThumbnail = {},
                )
            }
            columnSpacer(40.dp)
            item {
                TitleAndMusicContents(
                    title = stringResource(id = R.string.music_continue_play),
                    onClickShowAll = {},
                    musicItems = persistentListOf(
                        MusicItemModel(
                            title = "test",
                            thumbnail = "https://images.unsplash.com/photo-1693418161641-99928097b5ff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1760&q=80",
                            onClickMore = {},
                            currentSolvedCount = 0,
                            totalSolvedCount = 0,
                        )
                    ),
                    onClickMusicItem = {

                    },
                )
            }
            items(
                items = listOf(
                    RecommendationItem(
                        id = 0,
                        title = "test",
                        tag = null,
                        exams = listOf(
                            Exam.empty().copy(
                                id = 0,
                                title = "test",
                                thumbnailUrl = "https://images.unsplash.com/photo-1693418161641-99928097b5ff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1760&q=80",
                                solvedCount = 0,
                                heartCount = 0,
                                user = User.empty().copy(
                                    nickname = "test",
                                ),
                            )
                        ),
                    )
                ),
            ) { item ->
                MusicContentLayout(
                    modifier = Modifier
                        .padding(top = 40.dp),
                    title = item.title,
                    exams = item.exams.toImmutableList(),
                    onExamClicked = { },
                    isLoading = false,
                    onMoreClick = {
                    },
                )
            }
            columnSpacer(48.dp)
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isPullRefresh,
            state = pullRefreshState,
        )
    }
}

@Composable
private fun MusicContentLayout(
    modifier: Modifier = Modifier,
    title: String,
    exams: ImmutableList<Exam>,
    onExamClicked: (Int) -> Unit,
    onMoreClick: (Int) -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = modifier,
    ) {
        QuackText(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .skeleton(isLoading),
            text = title,
            typography = QuackTypography.Body1,
        )
        Spacer(modifier = Modifier.height(2.dp))
        QuackText(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .skeleton(isLoading),
            text = title,
            typography = QuackTypography.HeadLine1,
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
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
