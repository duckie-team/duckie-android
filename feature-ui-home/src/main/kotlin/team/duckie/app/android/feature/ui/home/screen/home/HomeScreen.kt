/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.home.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.home.HomeViewModel
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded

private val HomeHorizontalPadding = PaddingValues(horizontal = 16.dp)

@Composable
internal fun HomeScreen(
    vm: HomeViewModel = activityViewModel(),
    navigateToCreateProblem: () -> Unit,
    navigateToHomeDetail: (Int) -> Unit,
    navigateToSearch: (String) -> Unit,
) {
    val state = vm.collectAsState().value

    // handle HomeSideEffect
    LaunchedEffect(Unit) {
        vm.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.ReportError -> {
                    with(sideEffect.exception) {
                        printStackTrace()
                        reportToCrashlyticsIfNeeded()
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,
    ) {
        QuackCrossfade(
            targetState = state.homeSelectedIndex,
        ) { page ->
            when (page) {
                HomeStep.HomeRecommendScreen -> {
                    HomeRecommendScreen(
                        state = state,
                        homeViewModel = vm,
                        navigateToCreateProblem = navigateToCreateProblem,
                        navigateToHomeDetail = navigateToHomeDetail,
                        navigateToSearch = navigateToSearch,
                    )
                }

                HomeStep.HomeFollowingScreen -> {
                    if (state.isFollowingExist) {
                        HomeRecommendFollowingExamScreen(
                            modifier = Modifier.padding(HomeHorizontalPadding),
                            state = state,
                            navigateToHomeDetail = navigateToHomeDetail,
                            navigateToCreateProblem = navigateToCreateProblem,
                        )
                    } else {
                        HomeRecommendFollowingScreen(
                            modifier = Modifier.padding(HomeHorizontalPadding),
                            navigateToCreateProblem = navigateToCreateProblem,
                        )
                    }
                }
            }
        }
    }
}
