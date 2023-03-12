/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.home.screen.ranking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.home.component.HomeIconSize
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.dummy.skeletonExamineeItems
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMainTab

private const val ExamineePageIndex = 0
private const val ExamPageIndex = 1

@Composable
internal fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = activityViewModel(),
) {
    val tabs = remember { persistentListOf("응시자", "덕력고사") }
    var selectedTab by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeadLineTopAppBar(
            title = "명예의 전당",
            rightIcons = {
                QuackImage(
                    src = R.drawable.home_ic_create_24,
                    onClick = { },
                    size = HomeIconSize,
                )
            }
        )
        QuackMainTab(
            titles = tabs,
            selectedTabIndex = selectedTab,
            onTabSelected = {
                selectedTab = it
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        )
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            pageCount = tabs.size,
            key = { tabs[it] }
        ) { page ->
            when (page) {
                ExamineePageIndex -> {
                    ExamineeSection(skeletonExamineeItems)
                }

                ExamPageIndex -> {
                    ExamSection(
                        tags = persistentListOf(
                            Tag(id = 0, name= "전체"),
                            Tag(id = 1, name= "도로"),
                            Tag(id = 2, name= "상록"),
                        )
                    )
                }
            }
        }
    }
}
