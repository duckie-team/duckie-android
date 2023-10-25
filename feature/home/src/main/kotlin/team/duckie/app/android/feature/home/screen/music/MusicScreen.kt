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
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
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
import okhttp3.internal.immutableListOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.compose.isLastPage
import team.duckie.app.android.common.compose.scrollToOriginalPage
import team.duckie.app.android.common.compose.ui.DuckExamSmallCover
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.columnSpacer
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableType
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.viewmodel.music.MusicSideEffect
import team.duckie.app.android.feature.home.viewmodel.music.MusicViewModel
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
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

@Composable
internal fun MusicScreen(
    vm: MusicViewModel,
    setTargetExamId: (Int) -> Unit,
    onReport: () -> Unit,
    onShare: () -> Unit,
    navigateToExamDetail: (Int) -> Unit,
    navigateToViewAll: (Int, ExamType) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetDialogState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    DuckieSelectableBottomSheetDialog(
        modifier = Modifier.fillMaxSize(),
        bottomSheetState = bottomSheetDialogState,
        navigationBarsPaddingVisible = false,
        closeSheet = {
            coroutineScope.launch {
                bottomSheetDialogState.hide()
            }
        },
        onReport = onReport,
        onCopyLink = onShare,
        types = immutableListOf(DuckieSelectableType.CopyLink, DuckieSelectableType.Report),
    ) {
        MusicComponent(
            vm = vm,
            openExamBottomSheet = { exam ->
                setTargetExamId(exam)
                coroutineScope.launch {
                    bottomSheetDialogState.show()
                }
            },
            navigateToExamDetail = navigateToExamDetail,
            navigateToViewAll = navigateToViewAll,
        )
    }
}

@OptIn(ExperimentalToolbarApi::class)
@Composable
internal fun MusicComponent(
    vm: MusicViewModel,
    openExamBottomSheet: (Int) -> Unit,
    navigateToExamDetail: (Int) -> Unit,
    navigateToViewAll: (Int, ExamType) -> Unit,
) {
    val state = vm.collectAsState().value
    var isPullRefresh by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val heroModulePagerState = rememberPagerState {
        state.musicJumbotrons.size
    }
    val lazyListState = rememberLazyListState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val lazyMusicPagingItems = vm.musicRecommendations.collectAsLazyPagingItems()

    vm.collectSideEffect {
        when (it) {
            is MusicSideEffect.ListPullUp -> {
                lazyListState.animateScrollToItem(0)
                toolbarScaffoldState.toolbarState.expand(CollapsingToolbarExpandSpeed)
            }

            is MusicSideEffect.NavigateToMusicExamDetail -> {
                navigateToExamDetail(it.examId)
            }

            is MusicSideEffect.ReportError -> {
                with(it.exception) {
                    printStackTrace()
                    reportToCrashlyticsIfNeeded()
                }
            }

            is MusicSideEffect.NavigateToViewAll -> {
                navigateToViewAll(it.userId, ExamType.Continue)
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
                    items = state.musicJumbotrons,
                    onClickThumbnail = vm::clickMusicExam,
                )
            }
            columnSpacer(40.dp)
            itemsIndexed(
                items = lazyMusicPagingItems,
            ) { index, item ->
                if (index == 0) {
                    TitleAndMusicContents(
                        title = item?.title ?: "",
                        onClickShowAll = { vm.clickShowAll(item?.id ?: 0) },
                        exams = item?.exams?.toImmutableList() ?: persistentListOf(),
                        onClickExam = {
                            vm.clickMusicExam(it)
                        },
                        onClickMore = {
                            openExamBottomSheet(it.id)
                        },
                    )
                } else {
                    MusicContentLayout(
                        modifier = Modifier
                            .padding(top = 40.dp),
                        title = item?.title ?: "",
                        exams = item?.exams?.toImmutableList() ?: persistentListOf(),
                        onExamClicked = vm::clickMusicExam,
                        isLoading = false,
                        onMoreClick = openExamBottomSheet,
                    )
                }
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
    onExamClicked: (Exam) -> Unit,
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
                        onExamClicked(item)
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
