package land.sungbin.androidprojecttemplate.ui.main.setting.mvi

import land.sungbin.androidprojecttemplate.shared.android.base.UiEffect
import land.sungbin.androidprojecttemplate.shared.android.base.UiEvent
import land.sungbin.androidprojecttemplate.shared.android.base.UiState
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep

data class SettingState(
    val currentStep: SettingStep
): UiState


sealed class SettingSideEffect(

): UiEffect

sealed class SettingEvent(

): UiEvent