/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

// TODO(limsaehyun): 더미 데이터 때문에 MaxLineHeight 발생 추후에 제거 필요
@file:Suppress("MaxLineLength")

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.component.DuckieCircularProgressIndicator
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility

private val HomeHorizontalPadding = PaddingValues(
    horizontal = 16.dp,
)

@Composable
internal fun DuckieHomeScreen(
    vm: HomeViewModel = activityViewModel(),
    navigateToSearchResult: (String) -> Unit,
) {
    val state = vm.collectAsState().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendFollowingTest()
        vm.fetchJumbotrons()
    }

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,
    ) {
        QuackAnimatedVisibility(visible = state.isHomeLoading) {
            DuckieCircularProgressIndicator()
        }

        Crossfade(
            targetState = state.homeSelectedIndex,
        ) { page ->
            when (page) {
                HomeStep.HomeRecommendScreen -> {
                    HomeRecommendScreen(
                        navigateToSearchResult = navigateToSearchResult,
                    )
                }
                HomeStep.HomeFollowingScreen -> {
                    if (state.recommendFollowingTest.isNotEmpty()) {
                        HomeRecommendFollowingTestScreen(
                            modifier = Modifier.padding(HomeHorizontalPadding),
                        )
                    } else {
                        HomeRecommendFollowingScreen(
                            modifier = Modifier.padding(HomeHorizontalPadding),
                        )
                    }
                }
            }
        }
    }
}
