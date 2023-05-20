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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.HeadLineTopAppBar
import team.duckie.app.android.feature.home.component.HomeIconSize
import team.duckie.app.android.feature.home.constants.RankingPage
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingSideEffect
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.shared.ui.compose.Create
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun RankingScreen(
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
    val pagerState = rememberPagerState(initialPage = state.selectedTab)
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val bottomSheetDialogState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.setSelectedTab(pagerState.currentPage)
    }

    LaunchedEffect(Unit) {
        viewModel.refresh()
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
        Column(modifier = Modifier.fillMaxSize()) {
            HeadLineTopAppBar(
                title = stringResource(id = R.string.ranking),
                rightIcons = {
                    QuackImage(
                        src = QuackIcon.Create,
                        onClick = viewModel::clickAppBarRightIcon,
                        size = HomeIconSize,
                    )
                },
            )

            if (state.isError) {
                ErrorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    onRetryClick = viewModel::refresh,
                )
            } else {
                QuackMainTab(
                    titles = tabs,
                    selectedTabIndex = state.selectedTab,
                    onTabSelected = {
                        viewModel.setSelectedTab(it)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    },
                )
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    pageCount = tabs.size,
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
    }
}
