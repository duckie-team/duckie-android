/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.friends

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.ui.friends.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.feature.ui.friends.viewmodel.state.FriendsState
import team.duckie.app.android.shared.ui.compose.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.shared.ui.compose.UserFollowingLayout
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.icon.QuackIcon

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FriendsScreen(
    viewModel: FriendsViewModel,
    onPrevious: () -> Unit = { },
) {
    val context = LocalContext.current
    val tabs = remember {
        persistentListOf(
            context.getString(R.string.follower),
            context.getString(R.string.following),
        )
    }
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(initialPage = state.selectedTab.index)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .padding(systemBarPaddings),
    ) {
        BackPressedHeadLine2TopAppBar(
            title = "asd,",
            onBackPressed = onPrevious,
        )
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
            selectedTabIndex = state.selectedTab.index,
            onTabSelected = { index ->
                viewModel.setSelectedTab(FriendsType.fromIndex(index))
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
        )
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            pageCount = FriendsType.values().size,
            state = pagerState,
        ) {
            when(state.selectedTab) {
                FriendsType.Follower -> {
                    FriendSection(friends = state.followers, myUserId = 1)
                }
                FriendsType.Following -> {
                    FriendSection(friends = state.followings, myUserId = 1)
                }
            }
        }
    }
}

@Composable
private fun FriendSection(
    friends: ImmutableList<FriendsState.User>,
    myUserId: Int,
) {
    LazyColumn {
        items(friends) { item ->
            UserFollowingLayout(
                userId = item.userId,
                profileImgUrl = item.profileImgUrl,
                nickname = item.nickname ,
                favoriteTag = item.favoriteTag ,
                tier = item.tier,
                isFollowing = item.isFollowing ,
                onClickFollow = { follow ->
//                    onClickFollow(item.userId, follow)
                },
                isMine = myUserId == item.userId,
            )
        }
    }
}
