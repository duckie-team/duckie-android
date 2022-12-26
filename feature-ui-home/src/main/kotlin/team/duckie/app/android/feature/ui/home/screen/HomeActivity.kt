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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.home.component.DuckTestBottomNavigation
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

private const val HomeCrossFacadeLayoutId = "HomeCrossFacade"
private const val HomeBottomNavigationDividerLayoutId = "HomeBottomNavigationDivider"
private const val HomeBottomNavigationViewLayoutId = "HomeBottomNavigation"

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by homeViewModel.state.collectAsStateWithLifecycle()

            QuackTheme {
                Layout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background)
                        .padding(systemBarPaddings),
                    content = {
                        // TODO(limsaehyun): 추후에 QuackCrossfade 로 교체 필요
                        Crossfade(
                            modifier = Modifier.layoutId(HomeCrossFacadeLayoutId),
                            targetState = state.step,
                        ) { page ->
                            when (page) {
                                BottomNavigationStep.HomeScreen -> CompositionLocalProvider(
                                    LocalViewModel provides homeViewModel
                                ) {
                                    DuckieHomeScreen()
                                }
                                BottomNavigationStep.SearchScreen -> TODO("limsaehyun : 페이지 제작 후 연결 필요")
                                BottomNavigationStep.RankingScreen -> TODO("limsaehyun : 페이지 제작 후 연결 필요")
                                BottomNavigationStep.MyPageScreen -> TODO("limsaehyun : 페이지 제작 후 연결 필요")
                            }
                        }
                        // TODO(limsaehyun): 추후에 QuackDivider 로 교체 필요
                        Divider(
                            modifier = Modifier.layoutId(HomeBottomNavigationDividerLayoutId),
                            color = QuackColor.Gray3.composeColor,
                        )
                        DuckTestBottomNavigation(
                            modifier = Modifier.layoutId(HomeBottomNavigationViewLayoutId),
                            selectedIndex = state.step.index,
                            onClick = { index ->
                                homeViewModel.navigationPage(
                                    BottomNavigationStep.toStep(index)
                                )
                            },
                        )
                    },
                ) { measurables, constraints ->
                    val looseConstraints = constraints.asLoose()

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

                    val homeCrossFadeConstraints = constraints.copy(
                        minHeight = 0,
                        maxHeight = constraints.maxHeight - homeBottomNavigationHeight
                    )
                    val homeCrossFacadePlaceable = measurables.fastFirstOrNull { measurable ->
                        measurable.layoutId == HomeCrossFacadeLayoutId
                    }?.measure(homeCrossFadeConstraints) ?: npe()

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
