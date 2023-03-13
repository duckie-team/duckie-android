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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.home.component.HomeIconSize
import team.duckie.app.android.feature.ui.home.constants.RankingPage
import team.duckie.app.android.feature.ui.home.screen.ranking.viewmodel.RankingViewModel
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMainTab

@Composable
internal fun RankingScreen(viewModel: RankingViewModel) {
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

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.setSelectedTab(pagerState.currentPage)
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
                    ExamineeSection(viewModel = viewModel)
                }

                RankingPage.Exam.index -> {
                    ExamSection(viewModel = viewModel)
                }
            }
        }
    }
}
