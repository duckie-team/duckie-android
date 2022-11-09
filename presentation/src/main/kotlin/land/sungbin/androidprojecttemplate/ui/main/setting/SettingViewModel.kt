package land.sungbin.androidprojecttemplate.ui.main.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Singleton

@Singleton
internal class SettingViewModel {

    private val _currentStep =  MutableStateFlow(SettingStep.SETTING_MAIN_SCREEN)
    val currentStep = _currentStep.asStateFlow()

    fun navigatePage(step: SettingStep) {
        _currentStep.value = step
    }
}