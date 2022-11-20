package team.duckie.app.android.ui.main.setting.mvi

import team.duckie.app.android.domain.constants.AccountType
import team.duckie.app.ui.main.setting.utils.SettingStep

data class SettingState(
    val currentStep: SettingStep = SettingStep.SettingMainScreen,
    val activityNotifications: Boolean = false,
    val messageNotifications: Boolean = false,
    val accountType: team.duckie.app.android.domain.constants.AccountType = team.duckie.app.android.domain.constants.AccountType.DEFAULT,
    val email: String = "",
)

sealed class SettingSideEffect {
    object FetchSetting : SettingSideEffect()

    object PostSetting : SettingSideEffect()

    object FetchAccountInformation : SettingSideEffect()
}
