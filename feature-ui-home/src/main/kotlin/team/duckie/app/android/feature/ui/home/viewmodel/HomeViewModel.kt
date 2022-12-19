/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import team.duckie.app.android.feature.ui.home.screen.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.screen.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor() : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    fun navigationPage(
        step: BottomNavigationStep,
    ) {
        updateState { state ->
            state.copy(
                step = step,
            )
        }
    }

    fun changedSelectedTab(
        step: HomeStep,
    ) {
        updateState { state ->
            state.copy(
                selectedTabIndex = step,
            )
        }
    }
}
