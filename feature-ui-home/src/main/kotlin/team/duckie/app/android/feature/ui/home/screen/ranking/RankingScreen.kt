/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLifecycleComposeApi::class,
)

package team.duckie.app.android.feature.ui.home.screen.ranking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.home.component.HomeIconSize
import team.duckie.app.android.feature.ui.home.constants.RankingPage
import team.duckie.app.android.feature.ui.home.screen.ranking.sideeffect.RankingSideEffect
import team.duckie.app.android.feature.ui.home.screen.ranking.viewmodel.RankingViewModel
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMainTab

@Composable
internal fun RankingScreen(
    viewModel: RankingViewModel,
    navigateToCreateProblem: () -> Unit,
    navigateToDetail: (Int) -> Unit,
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

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.setSelectedTab(pagerState.currentPage)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchPopularTags()
        viewModel.getExams()
        viewModel.getUserRankings()
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
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeadLineTopAppBar(
            title = stringResource(id = R.string.ranking),
            rightIcons = {
                QuackImage(
                    src = R.drawable.home_ic_create_24,
                    onClick = viewModel::clickAppBarRightIcon,
                    size = HomeIconSize,
                )
            },
        )
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
                    )
                }
            }
        }
    }
}
