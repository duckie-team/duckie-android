/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.auth.usecase.ClearTokenUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.constans.SettingType.Companion.policyPages
import team.duckie.app.android.feature.setting.constans.Withdraweason
import team.duckie.app.android.feature.setting.viewmodel.sideeffect.SettingSideEffect
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState
import javax.inject.Inject

@HiltViewModel
internal class SettingViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
) : ContainerHost<SettingState, SettingSideEffect>, ViewModel() {

    override val container = container<SettingState, SettingSideEffect>(SettingState())

    init {
        initState()
    }

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

    fun updateWithdrawReason(reason: Withdraweason) = intent {
        reduce { state.copy(withdrawReasonSelected = reason) }
    }

    fun updateWithDrawFocus(isFocused: Boolean) = intent {
        reduce { state.copy(withdrawIsFocused = isFocused) }
    }

    fun updateWithdrawUserInputReason(reason: String) = intent {
        reduce { state.copy(withdrawUserInputReason = reason) }
    }

    fun changeLogoutDialogVisible(visible: Boolean) = intent {
        reduce { state.copy(logoutDialogVisible = visible) }
    }

    fun logout() = intent {
        clearTokenUseCase()
            .onSuccess {
                postSideEffect(SettingSideEffect.NavigateIntro)
            }
            .onFailure {
                postSideEffect(SettingSideEffect.ReportError(it))
            }
    }

    fun navigateBack() = intent {
        when (state.settingType) {
            SettingType.Main -> {
                postSideEffect(SettingSideEffect.NavigateBack)
            }
            SettingType.WithDraw -> {
                navigateStep(step = SettingType.AccountInfo)
            }
            in policyPages -> {
                navigateStep(step = SettingType.MainPolicy)
            }
            else -> {
                navigateStep(step = SettingType.Main)
            }
        }
    }

    /**
     * 설정 페이지를 업데이트 합니다.
     */
    fun navigateStep(step: SettingType) = intent {
        reduce { state.copy(settingType = step) }
    }

    /**
     * 오픈소스 라이센스 페이지로 이동합니다.
     */
    fun navigateOssLicense() = intent {
        postSideEffect(SettingSideEffect.NavigateOssLicense)
    }
}
