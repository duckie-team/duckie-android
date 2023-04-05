/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.guide

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class HomeGuideViewModel @Inject constructor() : ContainerHost<HomeGuideState, HomeGuideSideEffect>, ViewModel() {
    override val container = container<HomeGuideState, HomeGuideSideEffect>(HomeGuideState())

    fun updateGuideStared(started: Boolean) = intent {
        reduce { state.copy(isGuideStarted = started) }
    }
}
