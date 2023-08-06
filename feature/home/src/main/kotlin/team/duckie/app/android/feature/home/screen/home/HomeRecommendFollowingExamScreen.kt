/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.home.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.collectAndHandleState
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.HomeTopAppBar
import team.duckie.app.android.feature.home.constants.HomeStep
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.viewmodel.home.HomeState
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackBody3
import team.duckie.quackquack.ui.sugar.QuackSubtitle
import team.duckie.quackquack.ui.sugar.QuackSubtitle2

internal const val ThumbnailRatio = 4f / 3f

private val HomeProfileSize: DpSize = DpSize(
    width = 32.dp,
    height = 32.dp,
)

@Composable
internal fun HomeRecommendFollowingExamScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    vm: HomeViewModel = activityViewModel(),
    state: HomeState,
    navigateToCreateProblem: () -> Unit,
    navigateToHomeDetail: (Int) -> Unit,
    navigateToProfile: (Int) -> Unit,
) {
    val followingExam =
        vm.followingExam.collectAndHandleState(vm::handleLoadRecommendFollowingState)

    LaunchedEffect(Unit) {
        initState(MainScreenType.HomeRecommendFollowingExam) { vm.initFollowingExams() }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isHomeRecommendFollowingExamRefreshLoading,
        onRefresh = {
            vm.refreshRecommendFollowingExams(forceLoading = true)
        },
    )

    when (followingExam.itemCount) {
        0 -> HomeFollowingExamNotFoundScreen(
            homeSelectedIndex = state.homeSelectedIndex.index,
            changeHomeStep = { step ->
                vm.changedHomeScreen(step)
            },
            navigateToCreateProblem = navigateToCreateProblem,
        )

        else -> HomeRecommendFollowingSuccessScreen(
            modifier = modifier,
            pullRefreshState = pullRefreshState,
            state = state,
            vm = vm,
            followingExam = followingExam,
            navigateToCreateProblem = navigateToCreateProblem,
            navigateToHomeDetail = navigateToHomeDetail,
            navigateToProfile = navigateToProfile,
        )
    }
}

@Composable
private fun HomeRecommendFollowingSuccessScreen(
    modifier: Modifier = Modifier,
    pullRefreshState: PullRefreshState,
    state: HomeState,
    vm: HomeViewModel,
    followingExam: LazyPagingItems<HomeState.RecommendExam>,
    navigateToCreateProblem: () -> Unit,
    navigateToHomeDetail: (Int) -> Unit,
    navigateToProfile: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .pullRefresh(state = pullRefreshState),
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                HomeTopAppBar(
                    selectedTabIndex = state.homeSelectedIndex.index,
                    onTabSelected = { step ->
                        vm.changedHomeScreen(HomeStep.toStep(step))
                    },
                    onClickedCreate = navigateToCreateProblem,
                )
            }

            itemsIndexed(
                items = followingExam,
            ) { _, maker ->
                TestCoverWithMaker(
                    profile = maker?.owner?.profileImgUrl ?: "",
                    name = maker?.owner?.nickname ?: "",
                    title = maker?.title ?: "",
                    tier = maker?.owner?.tier ?: "",
                    favoriteTag = maker?.owner?.favoriteTag ?: "",
                    onUserProfileClick = {
                        navigateToProfile(maker?.owner?.userId ?: 0)
                    },
                    onTestClick = {
                        navigateToHomeDetail(maker?.examId ?: 0)
                    },
                    cover = maker?.coverUrl ?: "",
                    isLoading = state.isHomeRecommendFollowingExamLoading,
                )
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isHomeRecommendFollowingExamRefreshLoading,
            state = pullRefreshState,
        )
    }
}

@Composable
private fun TestCoverWithMaker(
    modifier: Modifier = Modifier,
    cover: String,
    profile: String,
    title: String,
    name: String,
    tier: String,
    favoriteTag: String,
    onTestClick: () -> Unit,
    isLoading: Boolean,
    onUserProfileClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(ThumbnailRatio)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .quackClickable {
                    onTestClick()
                }
                .skeleton(isLoading),
            model = cover,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        TestMakerLayout(
            modifier = Modifier.padding(top = 12.dp),
            profileImageUrl = profile,
            title = title,
            name = name,
            tier = tier,
            favoriteTag = favoriteTag,
            isLoading = isLoading,
            onUserProfileClick = onUserProfileClick,
            onLayoutClick = onTestClick,
        )
    }
}

@Composable
private fun TestMakerLayout(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    title: String,
    name: String,
    tier: String,
    favoriteTag: String,
    isLoading: Boolean,
    onLayoutClick: () -> Unit,
    onUserProfileClick: () -> Unit,
) {
    Row(
        modifier = modifier.quackClickable(
            rippleEnabled = false,
        ) {
            onLayoutClick()
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackProfileImage(
            modifier = Modifier.skeleton(isLoading),
            profileUrl = profileImageUrl,
            size = HomeProfileSize,
            onClick = onUserProfileClick,
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            QuackSubtitle2(
                modifier = Modifier.skeleton(isLoading),
                text = title,
            )
            Row(modifier = Modifier.padding(top = 4.dp)) {
                QuackBody3(
                    modifier = Modifier.skeleton(isLoading),
                    text = name,
                )
                Spacer(modifier = Modifier.width(8.dp))
                QuackText(
                    modifier = Modifier.skeleton(isLoading),
                    text = "$tier · $favoriteTag",
                    typography = QuackTypography.Body3.change(color = QuackColor.Gray2),
                )
            }
        }
    }
}

@Composable
private fun HomeFollowingExamNotFoundScreen(
    homeSelectedIndex: Int,
    changeHomeStep: (HomeStep) -> Unit,
    navigateToCreateProblem: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTopAppBar(
            selectedTabIndex = homeSelectedIndex,
            onTabSelected = { step ->
                changeHomeStep(HomeStep.toStep(step))
            },
            onClickedCreate = {
                navigateToCreateProblem()
            },
        )
        Spacer(space = 60.dp)
        QuackSubtitle(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.home_following_exam_not_found_title),
        )
        Spacer(space = 12.dp)
        QuackBody2(text = stringResource(id = R.string.home_following_exam_not_found_subtitle))
    }
}
