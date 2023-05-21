/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import team.duckie.app.android.feature.home.component.DuckTestBottomNavigation
import team.duckie.app.android.feature.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.home.screen.guide.HomeGuideScreen
import team.duckie.app.android.feature.home.screen.home.HomeScreen
import team.duckie.app.android.feature.home.screen.mypage.MyPageScreen
import team.duckie.app.android.feature.home.viewmodel.mypage.MyPageViewModel
import team.duckie.app.android.feature.home.screen.ranking.RankingScreen
import team.duckie.app.android.feature.home.screen.search.SearchMainScreen
import team.duckie.app.android.feature.home.viewmodel.MainState
import team.duckie.app.android.feature.home.viewmodel.MainViewModel
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.getPlaceable
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.quackquack.ui.color.QuackColor
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

    val mainGuidePlaceable = measurables.getPlaceable(MainGuideLayoutId, extraLooseConstraints)

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
    homeViewModel: HomeViewModel,
    state: MainState,
    navigateToUserProfile: (Int) -> Unit,
    navigateToEditProfile: (Int) -> Unit,
    navigateToTagEdit: (Int) -> Unit,
) {
    val navController = rememberNavController()

    Layout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .padding(systemBarPaddings),
        content = {
            HomeGuideScreen(
                modifier = Modifier.layoutId(MainGuideLayoutId),
                onClose = { mainViewModel.updateGuideVisible(visible = false) },
            )

            Box(Modifier.layoutId(MainCrossFacadeLayoutId)) {
                // https://developer.android.com/jetpack/compose/navigation?hl=ko
                NavHost(
                    navController = navController,
                    startDestination = BottomNavigationStep.HomeScreen.name,
                ) {
                    composable(BottomNavigationStep.HomeScreen.name) {
                        HomeScreen(
                            initState = mainViewModel::initState,
                            vm = homeViewModel,
                            navigateToSearch = { searchTag ->
                                mainViewModel.navigateToSearch(searchTag)
                            },
                            navigateToHomeDetail = { examId ->
                                mainViewModel.navigateToHomeDetail(examId)
                            },
                            navigateToCreateProblem = {
                                mainViewModel.navigateToCreateProblem()
                            },
                            setReportExamId = { examId ->
                                mainViewModel.setReportExamId(examId)
                            },
                            onReport = {
                                mainViewModel.report()
                            },
                        )
                    }

                    composable(BottomNavigationStep.SearchScreen.name) {
                        SearchMainScreen(initState = mainViewModel::initState)
                    }

                    composable(BottomNavigationStep.RankingScreen.name) {
                        RankingScreen(
                            initState = mainViewModel::initState,
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
                            setReportExamId = mainViewModel::setReportExamId,
                            onReport = mainViewModel::report,
                        )
                    }

                    composable(BottomNavigationStep.MyPageScreen.name) {
                        MyPageScreen(
                            initState = mainViewModel::initState,
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
                            navigateToFriend = { type: FriendsType, userId: Int, nickname: String ->
                                mainViewModel.navigateFriends(type, userId, nickname)
                            },
                            navigateToEditProfile = { userId ->
                                navigateToEditProfile(userId)
                            },
                            navigateToTagEdit = { userId ->
                                navigateToTagEdit(userId)
                            },
                        )
                    }
                }
            }

            QuackDivider(
                modifier = Modifier.layoutId(MainBottomNavigationDividerLayoutId),
            )
            DuckTestBottomNavigation(
                modifier = Modifier.layoutId(MainBottomNavigationViewLayoutId),
                selectedIndex = state.bottomNavigationStep.index,
                onClick = { index ->
                    navController.navigate(BottomNavigationStep.values()[index].name) {
                        navController.graph.findStartDestination().id.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

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
