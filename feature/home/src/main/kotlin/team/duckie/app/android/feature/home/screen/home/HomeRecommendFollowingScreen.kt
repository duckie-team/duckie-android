/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.content.UserFollowingLayout
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.HomeTopAppBar
import team.duckie.app.android.feature.home.constants.HomeStep
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.viewmodel.home.HomeState
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun HomeRecommendFollowingScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    vm: HomeViewModel = activityViewModel(),
    navigateToCreateExam: () -> Unit,
) {
    val state = vm.collectAsState().value

    LaunchedEffect(Unit) {
        initState(MainScreenType.HomeRecommendFollowing) { vm.fetchRecommendFollowing() }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            HomeTopAppBar(
                selectedTabIndex = state.homeSelectedIndex.index,
                onTabSelected = { step ->
                    vm.changedHomeScreen(HomeStep.toStep(step))
                },
                onClickedCreate = navigateToCreateExam,
                onClickedNotice = {},
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(213.dp),
                contentAlignment = Alignment.Center,
            ) {
                QuackText(
                    text = stringResource(id = R.string.home_following_initial_title),
                    typography = QuackTypography.Subtitle.change(textAlign = TextAlign.Center),
                )
            }
        }

        items(
            items = state.recommendFollowing,
        ) { categories ->
            HomeFollowingInitialRecommendUsers(
                modifier = Modifier.padding(bottom = 16.dp),
                myUserId = state.me?.id ?: 0,
                topic = categories.topic,
                recommendUser = categories.users,
                onClickFollowing = { userId, isFollowing ->
                    vm.followUser(userId, isFollowing)
                },
            )
        }
    }
}

@Composable
private fun HomeFollowingInitialRecommendUsers(
    modifier: Modifier = Modifier,
    topic: String,
    myUserId: Int,
    recommendUser: ImmutableList<HomeState.RecommendUserByTopic.User>,
    onClickFollowing: (Int, Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        QuackMaxWidthDivider()
        QuackTitle2(
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 12.dp,
            ),
            text = topic,
        )
        recommendUser.fastForEach { user ->
            UserFollowingLayout(
                userId = user.userId,
                visibleTrailingButton = myUserId != user.userId,
                profileImgUrl = user.profileImgUrl,
                nickname = user.nickname,
                favoriteTag = user.favoriteTag,
                tier = user.tier,
                isFollowing = user.isFollowing,
                onClickTrailingButton = {
                    onClickFollowing(user.userId, it)
                },
            )
        }
    }
}
