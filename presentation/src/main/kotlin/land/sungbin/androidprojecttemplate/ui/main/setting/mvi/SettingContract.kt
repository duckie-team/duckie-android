package land.sungbin.androidprojecttemplate.ui.main.setting.mvi

import land.sungbin.androidprojecttemplate.domain.constants.AccountType
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep

data class SettingState(
    val currentStep: SettingStep = SettingStep.SettingMainScreen,
    val activityNotifications: Boolean = false,
    val messageNotifications: Boolean = false,
    val accountType: AccountType = AccountType.DEFAULT,
    val email: String = "",
)

sealed class SettingSideEffect {
    object FetchSetting : SettingSideEffect()

    object PostSetting : SettingSideEffect()

    object FetchAccountInformation : SettingSideEffect()
}
