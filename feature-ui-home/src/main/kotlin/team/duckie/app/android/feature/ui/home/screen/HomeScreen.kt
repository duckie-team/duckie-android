/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.util.compose.activityViewModel

private val HomeHorizontalPadding = PaddingValues(horizontal = 16.dp)

@Composable
internal fun DuckieHomeScreen(
    vm: HomeViewModel = activityViewModel(),
) {
    val state = vm.collectAsState().value

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,
    ) {
        Crossfade(
            targetState = state.homeSelectedIndex,
        ) { page ->
            when (page) {
                HomeStep.HomeRecommendScreen -> {
                    HomeRecommendScreen()
                }
                HomeStep.HomeFollowingScreen -> {
                    if (state.isFollowingExist) {
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
