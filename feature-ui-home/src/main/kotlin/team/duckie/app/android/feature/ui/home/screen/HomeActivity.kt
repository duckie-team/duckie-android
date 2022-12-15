/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.home.common.DuckTestBottomNavigation
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

private const val HomeScreen: Int = 0
private const val SearchScreen: Int = 1
private const val RankingScreen: Int = 2
private const val MyPageScreen: Int = 3

private const val HomeCrossFacadeLayoutId = "HomeCrossFacade"
private const val HomeBottomNavigationDividerLayoutId = "HomeBottomNavigationDivider"
private const val HomeBottomNavigationViewLayoutId = "HomeBottomNavigation"

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                var selectedIndexed by remember { mutableStateOf(0) }

                Layout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background)
                        .padding(bottom = systemBarPaddings.calculateBottomPadding()),
                    content = {
                        Crossfade(
                            modifier = Modifier.layoutId(HomeCrossFacadeLayoutId),
                            targetState = selectedIndexed,
                        ) { page ->
                            when (page) {
                                HomeScreen -> DuckieHomeScreen()
                                SearchScreen -> TODO()
                                RankingScreen -> TODO()
                                MyPageScreen -> TODO()
                            }
                        }

                        // TODO(limsaehyun): 추후에 QuackDivider 로 교체 필요
                        Divider(
                            modifier = Modifier.layoutId(HomeBottomNavigationDividerLayoutId),
                            color = QuackColor.Gray3.composeColor,
                        )

                        DuckTestBottomNavigation(
                            modifier = Modifier.layoutId(HomeBottomNavigationViewLayoutId),
                            selectedIndex = selectedIndexed,
                            onClick = {
                                selectedIndexed = it
                            },
                        )
                    },
                ) { measurables, constraints ->

                    val looseConstraints = constraints.asLoose(width = true)

                    val homeCrossFacadePlaceable = measurables.fastFirstOrNull { measurable ->
                        measurable.layoutId == HomeCrossFacadeLayoutId
                    }?.measure(looseConstraints) ?: npe()

                    val homeBottomNavigationDividerPlaceable =
                        measurables.fastFirstOrNull { measurable ->
                            measurable.layoutId == HomeBottomNavigationDividerLayoutId
                        }?.measure(looseConstraints) ?: npe()

                    val homeBottomNavigationPlaceable = measurables.fastFirstOrNull { measurable ->
                        measurable.layoutId == HomeBottomNavigationViewLayoutId
                    }?.measure(looseConstraints) ?: npe()

                    val homeBottomNavigationHeight = homeBottomNavigationPlaceable.height
                    val homeBottomNavigationDividerHeight =
                        homeBottomNavigationDividerPlaceable.height

                    layout(
                        width = constraints.maxWidth,
                        height = constraints.maxHeight
                    ) {
                        homeCrossFacadePlaceable.place(
                            x = 0,
                            y = 0,
                        )

                        homeBottomNavigationDividerPlaceable.place(
                            x = 0,
                            y = constraints.maxHeight - homeBottomNavigationHeight - homeBottomNavigationDividerHeight
                        )

                        homeBottomNavigationPlaceable.place(
                            x = 0,
                            y = constraints.maxHeight - homeBottomNavigationHeight
                        )
                    }
                }
            }
        }
    }
}
