package land.sungbin.androidprojecttemplate.ui.main.setting.mvi

import land.sungbin.androidprojecttemplate.shared.android.base.UiEffect
import land.sungbin.androidprojecttemplate.shared.android.base.UiState
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.AccountType
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep

data class SettingState(
    val currentStep: SettingStep,
    val activityNotifications: Boolean,
    val messageNotifications: Boolean,
    val email: String,
    val instagram: String,
    val accountType: AccountType,
) : UiState

sealed class SettingSideEffect : UiEffect {
    object FetchSettingFailed : SettingSideEffect()

    object PostSettingFailed : SettingSideEffect()
}
