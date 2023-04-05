/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.friends

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.ui.friends.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.friends.constant.FriendsPage
import team.duckie.app.android.feature.ui.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.shared.ui.compose.Create
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun FriendsScreen(
    viewModel: FriendsViewModel,
) {
    val context = LocalContext.current
    val tabs = remember {
        persistentListOf(
            context.getString(R.string.follower),
            context.getString(R.string.following),
        )
    }
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(initialPage = state.selectedTab)
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        HeadLineTopAppBar(
            title = "",
            rightIcons = {
                QuackImage(
                    src = QuackIcon.ArrowBack,
                    onClick = viewModel::clickAppBarRightIcon,
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

            }
        }
    }
}
