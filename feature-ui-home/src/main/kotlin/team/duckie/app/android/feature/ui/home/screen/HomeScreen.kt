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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.shared.ui.compose.TextTabLayout
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.util.DpSize

private val HomeIconSize = DpSize(24.dp)

private val HomeHorizontalPadding = PaddingValues(
    horizontal = 16.dp,
)

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun DuckieHomeScreen() = CoroutineScopeContent {
    val vm = LocalViewModel.current as HomeViewModel
    val state = vm.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendFollowingTest()
    }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Crossfade(
            targetState = state.homeSelectedIndex,
        ) { page ->
            when (page) {
                HomeStep.HomeRecommendScreen -> {
                    HomeRecommendScreen()
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

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onClickedEdit: () -> Unit,
) {

    val homeTextTabTitles = persistentListOf(
        stringResource(id = R.string.recommend),
        stringResource(id = R.string.following),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextTabLayout(
            titles = homeTextTabTitles,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
        )
        QuackImage(
            src = R.drawable.home_ic_create_24,
            onClick = onClickedEdit,
            size = HomeIconSize,
        )
    }
}
