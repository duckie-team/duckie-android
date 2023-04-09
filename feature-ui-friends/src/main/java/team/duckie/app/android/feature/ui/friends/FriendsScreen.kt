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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.shared.ui.compose.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.shared.ui.compose.UserFollowingLayout
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackMainTab

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
            when (state.selectedTab) {
                FriendsType.Follower -> {
                    FriendSection(
                        friends = state.followers,
                        myUserId = state.me?.id ?: 0,
                    )
                }

                FriendsType.Following -> {
                    FriendSection(
                        friends = state.followings,
                        myUserId = state.me?.id ?: 0,
                    )
                }
            }
        }
    }
}

@Composable
private fun FriendSection(
    friends: ImmutableList<User>,
    myUserId: Int,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        items(friends) { item ->
            UserFollowingLayout(
                userId = item.id,
                profileImgUrl = item.profileImageUrl ?: "",
                nickname = item.nickname,
                favoriteTag = item.duckPower?.tag?.name ?: "",
                tier = item.duckPower?.tier ?: "",
                isFollowing = item.follow != null,
                onClickFollow = { follow ->
//                    onClickFollow(item.userId, follow)
                },
                isMine = myUserId == item.id,
            )
        }
    }
}
