package land.sungbin.androidprojecttemplate.ui.main.setting.vm

import land.sungbin.androidprojecttemplate.shared.android.base.BaseViewModel
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingSideEffect
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingState
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.AccountType
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class SettingViewModel @Inject constructor(

): BaseViewModel<SettingState, SettingSideEffect>() {

    fun navigatePage(step: SettingStep) = setState {
        copy(
            currentStep = step,
        )
    }

    fun changeActivityNotifications(state: Boolean) = setState {
        copy(
            activityNotifications = state,
        )
    }

    fun changeMessageNotifications(state: Boolean) = setState {
        copy(
            messageNotifications = state,
        )
    }

    override fun createInitialState(): SettingState {
        return SettingState(
            currentStep = SettingStep.SETTING_MAIN_SCREEN,
            activityNotifications = false,
            messageNotifications = false,
            email = "sh007100@naver.com",
            instagram = "limsaehyun",
            accountType = AccountType.KAKAO,
        )
    }
}