package land.sungbin.androidprojecttemplate.ui.main.setting.vm

import land.sungbin.androidprojecttemplate.shared.android.base.BaseViewModel
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingEvent
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingSideEffect
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingState
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingViewModel @Inject constructor(

): BaseViewModel<SettingEvent, SettingState, SettingSideEffect>() {

    fun navigatePage(step: SettingStep) = reducer {
        copy(
            currentStep = step,
        )
    }

    override fun createInitialState(): SettingState {
        return SettingState(
            currentStep = SettingStep.SETTING_MAIN_SCREEN,
        )
    }
}