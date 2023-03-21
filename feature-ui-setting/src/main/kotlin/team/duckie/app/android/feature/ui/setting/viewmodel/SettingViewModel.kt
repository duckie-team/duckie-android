/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.setting.viewmodel.sideeffect.SettingSideEffect
import team.duckie.app.android.feature.ui.setting.viewmodel.state.SettingState
import javax.inject.Inject

@HiltViewModel
internal class SettingViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
) : ContainerHost<SettingState, SettingSideEffect>, ViewModel() {

    override val container = container<SettingState, SettingSideEffect>(SettingState())

    /** [SettingViewModel]의 초기 상태를 설정한다. */
    private fun initState() = intent {
        getMeUseCase()
            .onSuccess {
                reduce { state.copy(me = it) }
            }
            .onFailure {
                postSideEffect(SettingSideEffect.ReportError(it))
            }
    }
}
