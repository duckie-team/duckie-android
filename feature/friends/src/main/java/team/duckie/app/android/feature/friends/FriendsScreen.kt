/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.friends

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.NoItemScreen
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.content.UserFollowingLayout
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.feature.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.feature.friends.viewmodel.state.FriendsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackTab
import team.duckie.quackquack.ui.QuackTabColors
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun FriendScreen(
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
    val pagerState = rememberPagerState(
        initialPage = state.friendType.index,
        pageCount = { FriendsType.values().size },
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.value)
            .padding(systemBarPaddings),
    ) {
        BackPressedHeadLine2TopAppBar(
            title = state.targetName,
            onBackPressed = onPrevious,
        )
        QuackTab(
            index = pagerState.currentPage,
            colors = QuackTabColors.defaultTabColors(),
        ) {
            tabs.forEach { label ->
                tab(label) { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) { index ->
            if (state.isError) {
                ErrorScreen(
                    Modifier,
                    false,
                    onRetryClick = { viewModel.initState() },
                )
            } else {
                FriendScreenInternal(
                    isLoading = state.isLoading,
                    isMine = state.isMine,
                    type = FriendsType.fromIndex(index),
                    state = state,
                    onClickFollowByFollower = { userId, follow ->
                        viewModel.followUser(
                            userId = userId,
                            isFollowing = follow,
                            type = FriendsType.Follower,
                        )
                    },
                    onClickFollowByFollowing = { userId, follow ->
                        viewModel.followUser(
                            userId = userId,
                            isFollowing = follow,
                            type = FriendsType.Following,
                        )
                    },
                    onClickUserProfile = { userId ->
                        viewModel.navigateToUserProfile(userId)
                    },
                )
            }
        }
    }
}

@Composable
private fun FriendScreenInternal(
    isLoading: Boolean,
    isMine: Boolean,
    type: FriendsType,
    state: FriendsState,
    onClickFollowByFollower: (Int, Boolean) -> Unit,
    onClickFollowByFollowing: (Int, Boolean) -> Unit,
    onClickUserProfile: (Int) -> Unit,
) {
    when (type) {
        FriendsType.Follower -> {
            if (state.followers.isEmpty()) {
                FriendNotFoundScreen(
                    isLoading = isLoading,
                    isMine = isMine,
                    type = type,
                    nickname = state.me?.nickname ?: "",
                )
            } else {
                FriendListScreen(
                    isLoading = isLoading,
                    friends = state.followers,
                    myUserId = state.me?.id ?: 0,
                    onClickFollow = onClickFollowByFollower,
                    onClickUserProfile = onClickUserProfile,
                )
            }
        }

        FriendsType.Following -> {
            if (state.followings.isEmpty()) {
                FriendNotFoundScreen(
                    isLoading = isLoading,
                    isMine = isMine,
                    type = type,
                    nickname = state.me?.nickname ?: "",
                )
            } else {
                FriendListScreen(
                    isLoading = isLoading,
                    friends = state.followings,
                    myUserId = state.me?.id ?: 0,
                    onClickFollow = onClickFollowByFollowing,
                    onClickUserProfile = onClickUserProfile,
                )
            }
        }
    }
}

@Composable
private fun FriendNotFoundScreen(
    isLoading: Boolean,
    isMine: Boolean,
    type: FriendsType,
    nickname: String, // [isMine] == true 일 때만 사용
) {
    val isFollower = when (type) {
        FriendsType.Follower -> true
        FriendsType.Following -> false
    }

    if (isMine) {
        MyPageFriendNotFoundScreen(
            isLoading = isLoading,
            title = stringResource(
                id = if (isFollower) {
                    R.string.my_page_follower_not_found
                } else {
                    R.string.my_page_following_not_found
                },
                nickname,
            ),
        )
    } else {
        NoItemScreen(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally),
            title = stringResource(
                id = if (isFollower) {
                    R.string.follower_not_found_title
                } else {
                    R.string.following_not_found_title
                },
            ),
            description = stringResource(
                id = if (isFollower) {
                    R.string.follower_not_found_description
                } else {
                    R.string.following_not_found_description
                },
            ),
            isLoading = isLoading,
        )
    }
}

@Composable
private fun MyPageFriendNotFoundScreen(
    isLoading: Boolean,
    title: String,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(space = 74.5.dp)
        QuackText(
            modifier = Modifier.skeleton(isLoading),
            text = title,
            typography = QuackTypography.HeadLine2.change(
                color = QuackColor.Gray1,
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Composable
private fun FriendListScreen(
    isLoading: Boolean,
    friends: ImmutableList<FriendsState.Friend>,
    myUserId: Int,
    onClickFollow: (Int, Boolean) -> Unit,
    onClickUserProfile: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(friends) { item ->
            UserFollowingLayout(
                isLoading = isLoading,
                userId = item.userId,
                profileImgUrl = item.profileImgUrl,
                nickname = item.nickname,
                favoriteTag = item.favoriteTag,
                tier = item.tier,
                isFollowing = item.isFollowing,
                onClickTrailingButton = { follow ->
                    onClickFollow(item.userId, follow)
                },
                visibleTrailingButton = myUserId != item.userId,
                onClickUserProfile = onClickUserProfile,
            )
        }
    }
}
