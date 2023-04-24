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
import team.duckie.app.android.feature.ui.home.viewmodel.MainState
import team.duckie.app.android.feature.ui.home.viewmodel.MainViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.getPlaceable
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.quackquack.ui.component.QuackDivider

private const val MainCrossFacadeLayoutId = "MainCrossFacade"
private const val MainBottomNavigationDividerLayoutId = "MainBottomNavigationDivider"
private const val MainBottomNavigationViewLayoutId = "MainBottomNavigationView"
private const val MainGuideLayoutId = "MainGuide"

private fun getMainScreenMeasurePolicy(
    guideVisible: Boolean,
) = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(height = true)

    val mainBottomNavigationDividerPlaceable =
        measurables.getPlaceable(MainBottomNavigationDividerLayoutId, looseConstraints)

    val mainBottomNavigationPlaceable =
        measurables.getPlaceable(MainBottomNavigationViewLayoutId, looseConstraints)

    val mainBottomNavigationHeight = mainBottomNavigationPlaceable.height

    val mainBottomNavigationDividerHeight = mainBottomNavigationDividerPlaceable.height

    val mainCrossFadeConstraints = constraints.copy(
        minHeight = 0,
        maxHeight = constraints.maxHeight - mainBottomNavigationHeight,
    )
    val mainCrossFacadePlaceable =
        measurables.getPlaceable(MainCrossFacadeLayoutId, mainCrossFadeConstraints)

    val mainGuidePlaceable =
        measurables.getPlaceable(MainGuideLayoutId, extraLooseConstraints)

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        mainCrossFacadePlaceable.place(
            x = 0,
            y = 0,
        )

        mainBottomNavigationDividerPlaceable.place(
            x = 0,
            y = constraints.maxHeight - mainBottomNavigationHeight - mainBottomNavigationDividerHeight,
        )

        mainBottomNavigationPlaceable.place(
            x = 0,
            y = constraints.maxHeight - mainBottomNavigationHeight,
        )
        if (guideVisible) {
            mainGuidePlaceable.place(
                x = 0,
                y = 0,
            )
        }
    }
}

@Composable
internal fun MainScreen(
    mainViewModel: MainViewModel,
    rankingViewModel: RankingViewModel,
    myPageViewModel: MyPageViewModel,
    state: MainState,
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
                modifier = Modifier.layoutId(MainGuideLayoutId),
                onClose = { mainViewModel.updateGuideVisible(visible = false) },
            )
            QuackCrossfade(
                modifier = Modifier.layoutId(MainCrossFacadeLayoutId),
                targetState = state.bottomNavigationStep,
            ) { page ->
                when (page) {
                    BottomNavigationStep.HomeScreen -> HomeScreen(
                        navigateToSearch = { searchTag ->
                            mainViewModel.navigateToSearch(searchTag)
                        },
                        navigateToHomeDetail = { examId ->
                            mainViewModel.navigateToHomeDetail(examId)
                        },
                        navigateToCreateProblem = {
                            mainViewModel.navigateToCreateProblem()
                        },
                    )

                    BottomNavigationStep.SearchScreen -> SearchMainScreen()
                    BottomNavigationStep.RankingScreen -> RankingScreen(
                        viewModel = rankingViewModel,
                        navigateToCreateProblem = {
                            mainViewModel.navigateToCreateProblem()
                        },
                        navigateToDetail = { examId ->
                            mainViewModel.navigateToHomeDetail(examId)
                        },
                        navigateToUserProfile = { userId ->
                            navigateToUserProfile(userId)
                        },
                    )

                    BottomNavigationStep.MyPageScreen -> MyPageScreen(
                        viewModel = myPageViewModel,
                        navigateToSetting = {
                            mainViewModel.navigateToSetting()
                        },
                        navigateToNotification = {
                            mainViewModel.navigateToNotification()
                        },
                        navigateToExam = { examId ->
                            mainViewModel.navigateToHomeDetail(examId)
                        },
                        navigateToCreateProblem = {
                            mainViewModel.navigateToCreateProblem()
                        },
                        navigateToSearch = { searchTag ->
                            mainViewModel.navigateToSearch(searchTag)
                        },
                        navigateToFriend = { type: FriendsType, userId: Int ->
                            mainViewModel.navigateFriends(type, userId)
                        },
                        navigateToEditProfile = { userId ->
                            navigateToEditProfile(userId)
                        },
                    )
                }
            }
            QuackDivider(
                modifier = Modifier.layoutId(MainBottomNavigationDividerLayoutId),
            )
            DuckTestBottomNavigation(
                modifier = Modifier.layoutId(MainBottomNavigationViewLayoutId),
                selectedIndex = state.bottomNavigationStep.index,
                onClick = { index ->
                    mainViewModel.navigationPage(
                        BottomNavigationStep.toStep(index),
                    )
                },
            )
        },
        measurePolicy = getMainScreenMeasurePolicy(
            guideVisible = state.guideVisible,
        ),
    )
}
