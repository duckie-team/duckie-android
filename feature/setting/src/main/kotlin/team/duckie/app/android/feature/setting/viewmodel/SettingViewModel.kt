/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.kotlin.seconds
import team.duckie.app.android.domain.auth.usecase.ClearTokenUseCase
import team.duckie.app.android.domain.exam.usecase.CancelExamIgnoreUseCase
import team.duckie.app.android.domain.exam.usecase.GetExamIgnoresUseCase
import team.duckie.app.android.domain.ignore.usecase.CancelUserIgnoreUseCase
import team.duckie.app.android.domain.me.usecase.GetIsStageUseCase
import team.duckie.app.android.domain.user.usecase.FetchIgnoreUsersUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.constans.SettingType.Companion.policyPages
import team.duckie.app.android.feature.setting.constans.Withdraweason
import team.duckie.app.android.feature.setting.viewmodel.sideeffect.SettingSideEffect
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState
import javax.inject.Inject

private const val WithdrawCommingSoonMessage: String = "회원탈퇴는 아직 준비중인 기능입니다."
private const val ClickCount: Int = 4

@HiltViewModel
internal class SettingViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
    private val getIsStageUseCase: GetIsStageUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
    private val fetchIgnoreUsers: FetchIgnoreUsersUseCase,
    private val cancelUserIgnoreUseCase: CancelUserIgnoreUseCase,
    private val getIgnoreExamsUseCase: GetExamIgnoresUseCase,
    private val cancelExamIgnoreUseCase: CancelExamIgnoreUseCase,
) : ContainerHost<SettingState, SettingSideEffect>, ViewModel() {

    override val container = container<SettingState, SettingSideEffect>(SettingState())

    private var clickCount = 0
    private var lastClickTime = 0L
    private var isStage = false

    init {
        initState()
    }

    /** [SettingViewModel]의 초기 상태를 설정한다. */
    private fun initState() = intent {
        isStage = getIsStageUseCase().getOrDefault(false)

        getMeUseCase()
            .onSuccess {
                reduce { state.copy(me = it, isStage = isStage) }
            }
            .onFailure {
                postSideEffect(SettingSideEffect.ReportError(it))
            }
    }

    fun getIgnoreExams() = intent {
        getIgnoreExamsUseCase()
            .onSuccess { exams ->
                reduce { state.copy(ignoreExams = exams.toImmutableList()) }
            }
            .onFailure {
                postSideEffect(SettingSideEffect.ReportError(it))
            }
    }

    fun cancelIgnoreExam(examId: Int) = intent {
        cancelExamIgnoreUseCase(examId = examId)
            .onSuccess {
                val ignoreExams = state.ignoreExams.filter { it.id != examId }.toImmutableList()
                reduce { state.copy(ignoreExams = ignoreExams) }
            }
            .onFailure {
                postSideEffect(SettingSideEffect.ReportError(it))
            }
    }

    fun cancelIgnoreUser(userId: Int) = intent {
        cancelUserIgnoreUseCase(targetId = userId)
            .onSuccess {
                val ignoreUsers = state.ignoreUsers.filter { it.id != userId }.toImmutableList()
                reduce { state.copy(ignoreUsers = ignoreUsers) }
            }
            .onFailure {
                postSideEffect(SettingSideEffect.ReportError(it))
            }
    }

    fun getIgnoreUsers() = intent {
        fetchIgnoreUsers()
            .onSuccess { users ->
                reduce { state.copy(ignoreUsers = users) }
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

    fun changeDevModeDialogVisible(visible: Boolean) {
        if (visible) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < 2.seconds) {
                clickCount++
                if (clickCount >= ClickCount) {
                    clickCount = 0
                    intent { reduce { state.copy(devModeDialogVisible = true) } }
                }
            } else {
                clickCount = 0
            }
            lastClickTime = currentTime
        } else {
            intent { reduce { state.copy(devModeDialogVisible = false) } }
        }
    }

    fun changeLogoutDialogVisible(visible: Boolean) = intent {
        reduce { state.copy(logoutDialogVisible = visible) }
    }

    fun changeWithdrawDialogVisible(visible: Boolean) = intent {
        reduce { state.copy(withdrawDialogVisible = visible) }
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

    fun withdraw() = intent { // TODO(limsaehyun): 회원탈퇴 로직 구현해야 함
        postSideEffect(SettingSideEffect.ShowToast(WithdrawCommingSoonMessage))
        changeWithdrawDialogVisible(visible = false)
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
     * 덕키 마켓 페이지로 이동합니다.
     */
    fun goToMarket() = intent {
        postSideEffect(SettingSideEffect.NavigatePlayStoreMarket)
    }

    /**
     * 오픈소스 라이센스 페이지로 이동합니다.
     */
    fun navigateOssLicense() = intent {
        postSideEffect(SettingSideEffect.NavigateOssLicense)
    }
}
