/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import team.duckie.app.android.feature.ui.home.component.DuckTestBottomNavigation
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.screen.guide.HomeGuideScreen
import team.duckie.app.android.feature.ui.home.screen.home.HomeScreen
import team.duckie.app.android.feature.ui.home.screen.mypage.MyPageScreen
import team.duckie.app.android.feature.ui.home.viewmodel.mypage.MyPageViewModel
import team.duckie.app.android.feature.ui.home.screen.ranking.RankingScreen
import team.duckie.app.android.feature.ui.home.screen.search.SearchMainScreen
import team.duckie.app.android.feature.ui.home.viewmodel.DuckieHomeState
import team.duckie.app.android.feature.ui.home.viewmodel.DuckieHomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.getPlaceable
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.quackquack.ui.component.QuackDivider

private const val HomeCrossFacadeLayoutId = "HomeCrossFacade"
private const val HomeBottomNavigationDividerLayoutId = "HomeBottomNavigationDivider"
private const val HomeBottomNavigationViewLayoutId = "HomeBottomNavigation"
private const val HomeGuideLayoutId = "HomeGuideLayoutId"

private fun getHomeMeasurePolicy(
    guideVisible: Boolean,
) = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(height = true)

    val homeBottomNavigationDividerPlaceable =
        measurables.getPlaceable(HomeBottomNavigationDividerLayoutId, looseConstraints)

    val homeBottomNavigationPlaceable =
        measurables.getPlaceable(HomeBottomNavigationViewLayoutId, looseConstraints)

    val homeBottomNavigationHeight = homeBottomNavigationPlaceable.height

    val homeBottomNavigationDividerHeight = homeBottomNavigationDividerPlaceable.height

    val homeCrossFadeConstraints = constraints.copy(
        minHeight = 0,
        maxHeight = constraints.maxHeight - homeBottomNavigationHeight,
    )
    val homeCrossFacadePlaceable =
        measurables.getPlaceable(HomeCrossFacadeLayoutId, homeCrossFadeConstraints)

    val homeGuidePlaceable =
        measurables.getPlaceable(HomeGuideLayoutId, extraLooseConstraints)

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        homeCrossFacadePlaceable.place(
            x = 0,
            y = 0,
        )

        homeBottomNavigationDividerPlaceable.place(
            x = 0,
            y = constraints.maxHeight - homeBottomNavigationHeight - homeBottomNavigationDividerHeight,
        )

        homeBottomNavigationPlaceable.place(
            x = 0,
            y = constraints.maxHeight - homeBottomNavigationHeight,
        )
        if (guideVisible) {
            homeGuidePlaceable.place(
                x = 0,
                y = 0,
            )
        }
    }
}

@Composable
internal fun DuckieHomeScreen(
    duckieHomeViewModel: DuckieHomeViewModel,
    rankingViewModel: RankingViewModel,
    myPageViewModel: MyPageViewModel,
    state: DuckieHomeState,
    navigateToUserProfile: (Int) -> Unit,
    navigateToEditProfile: (Int) -> Unit,
) {
    Layout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(systemBarPaddings),
        content = {
            HomeGuideScreen(
                modifier = Modifier.layoutId(HomeGuideLayoutId),
                onClose = { duckieHomeViewModel.updateGuideVisible(visible = false) },
            )
            QuackCrossfade(
                modifier = Modifier.layoutId(HomeCrossFacadeLayoutId),
                targetState = state.bottomNavigationStep,
            ) { page ->
                when (page) {
                    BottomNavigationStep.HomeScreen -> HomeScreen(
                        navigateToSearch = { searchTag ->
                            duckieHomeViewModel.navigateToSearch(searchTag)
                        },
                        navigateToHomeDetail = { examId ->
                            duckieHomeViewModel.navigateToHomeDetail(examId)
                        },
                        navigateToCreateProblem = {
                            duckieHomeViewModel.navigateToCreateProblem()
                        },
                    )

                    BottomNavigationStep.SearchScreen -> SearchMainScreen()
                    BottomNavigationStep.RankingScreen -> RankingScreen(
                        viewModel = rankingViewModel,
                        navigateToCreateProblem = {
                            duckieHomeViewModel.navigateToCreateProblem()
                        },
                        navigateToDetail = { examId ->
                            duckieHomeViewModel.navigateToHomeDetail(examId)
                        },
                        navigateToUserProfile = { userId ->
                            navigateToUserProfile(userId)
                        },
                    )

                    BottomNavigationStep.MyPageScreen -> MyPageScreen(
                        viewModel = myPageViewModel,
                        navigateToSetting = {
                            duckieHomeViewModel.navigateToSetting()
                        },
                        navigateToNotification = {
                            duckieHomeViewModel.navigateToNotification()
                        },
                        navigateToExam = { examId ->
                            duckieHomeViewModel.navigateToHomeDetail(examId)
                        },
                        navigateToCreateProblem = {
                            duckieHomeViewModel.navigateToCreateProblem()
                        },
                        navigateToSearch = { searchTag ->
                            duckieHomeViewModel.navigateToSearch(searchTag)
                        },
                        navigateToFriend = { type: FriendsType, userId: Int ->
                            duckieHomeViewModel.navigateFriends(type, userId)
                        },
                        navigateToEditProfile = { userId ->
                            navigateToEditProfile(userId)
                        },
                    )
                }
            }
            QuackDivider(
                modifier = Modifier.layoutId(HomeBottomNavigationDividerLayoutId),
            )
            DuckTestBottomNavigation(
                modifier = Modifier.layoutId(HomeBottomNavigationViewLayoutId),
                selectedIndex = state.bottomNavigationStep.index,
                onClick = { index ->
                    duckieHomeViewModel.navigationPage(
                        BottomNavigationStep.toStep(index),
                    )
                },
            )
        },
        measurePolicy = getHomeMeasurePolicy(
            guideVisible = state.guideVisible,
        ),
    )
}
