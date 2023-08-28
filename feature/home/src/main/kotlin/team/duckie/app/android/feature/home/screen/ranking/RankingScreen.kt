/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
)

package team.duckie.app.android.feature.home.screen.ranking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.HeadLineTopAppBar
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.constants.RankingPage
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingSideEffect
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingViewModel
import team.duckie.quackquack.ui.QuackTab

@Composable
internal fun RankingScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    viewModel: RankingViewModel,
    navigateToCreateProblem: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateToUserProfile: (Int) -> Unit,
    onReport: () -> Unit,
    setReportExamId: (Int) -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val tabs = remember {
        persistentListOf(
            context.getString(R.string.examinee),
            context.getString(R.string.exam),
        )
    }
    val pagerState = rememberPagerState(
        initialPage = state.selectedTab,
        pageCount = { tabs.size },
    )
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val bottomSheetDialogState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var isPullRefresh by remember { mutableStateOf(false) }

    @AllowMagicNumber
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isPullRefresh,
        onRefresh = {
            isPullRefresh = true
            viewModel.refresh()
            coroutineScope.launch {
                delay(1000)
                isPullRefresh = false
            }
        },
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.setSelectedTab(pagerState.currentPage)
    }

    LaunchedEffect(Unit) {
        initState(MainScreenType.Ranking) { viewModel.refresh() }
    }

    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is RankingSideEffect.ReportError -> {
                    Firebase.crashlytics.recordException(sideEffect.exception)
                }

                RankingSideEffect.NavigateToCreateProblem -> {
                    navigateToCreateProblem()
                }

                is RankingSideEffect.NavigateToExamDetail -> {
                    navigateToDetail(sideEffect.examId)
                }

                is RankingSideEffect.ListPullUp -> {
                    when (sideEffect.currentTab) {
                        RankingPage.Exam.index -> {
                            lazyGridState.animateScrollToItem(0)
                        }

                        RankingPage.Examinee.index -> {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                }

                is RankingSideEffect.NavigateToUserProfile -> {
                    navigateToUserProfile(sideEffect.userId)
                }
            }
        }
    }

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
    ) {
        Box(Modifier.pullRefresh(pullRefreshState)) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                HeadLineTopAppBar(
                    title = stringResource(id = R.string.ranking),
//                  TODO(limsaehyun): 시험 생성하기가 가능한 스펙에서 활용
//                  rightIcons = {
//                      QuackImage(
//                          src = QuackIcon.Create,
//                          onClick = viewModel::clickAppBarRightIcon,
//                          size = HomeIconSize,
//                      )
//                  },
                )

                if (state.isError) {
                    ErrorScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding(),
                        onRetryClick = viewModel::refresh,
                    )
                } else {
                    QuackTab(index = state.selectedTab) {
                        tabs.fastForEach { label ->
                            tab(label) { index ->
                                viewModel.setSelectedTab(index)
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        }
                    }
                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        state = pagerState,
                        key = { tabs[it] },
                    ) { page ->
                        when (page) {
                            RankingPage.Examinee.index -> {
                                ExamineeSection(
                                    viewModel = viewModel,
                                    lazyListState = lazyListState,
                                )
                            }

                            RankingPage.Exam.index -> {
                                ExamSection(
                                    viewModel = viewModel,
                                    lazyGridState = lazyGridState,
                                    openReportDialog = { exam ->
                                        setReportExamId(exam)
                                        coroutineScope.launch {
                                            bottomSheetDialogState.show()
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = isPullRefresh,
                state = pullRefreshState,
            )
        }
    }
}
