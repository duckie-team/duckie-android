/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.state

import team.duckie.app.android.feature.ui.home.screen.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.screen.HomeStep

data class HomeState(
    val step: BottomNavigationStep = BottomNavigationStep.HomeScreen,
    val selectedTabIndex: HomeStep = HomeStep.HomeRecommendScreen,
)
