package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import land.sungbin.androidprojecttemplate.shared.compose.extension.CoroutineScopeContent
import land.sungbin.androidprojecttemplate.shared.compose.extension.launch
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingSideEffect
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep
import land.sungbin.androidprojecttemplate.ui.main.setting.vm.SettingViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun SettingScreen(
    settingVM: SettingViewModel,
) = CoroutineScopeContent {

    val state = settingVM.state.collectAsStateWithLifecycle().value
    val effect = settingVM.effect

    LaunchedEffect(effect) {

        effect.collect { effect ->
            when (effect) {
                SettingSideEffect.FetchSettingFailed -> {
                }
                SettingSideEffect.PostSettingFailed -> {
                }
                SettingSideEffect.FetchAccountInformationFailed -> {
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        settingVM.fetchSetting()
        settingVM.fetchAccountInformation()
    }

    Crossfade(
        targetState = state.currentStep
    ) { page ->
        when (page) {
            SettingStep.SETTING_MAIN_SCREEN -> {
                SettingMainScreen(
                    onClickBack = {
                    },
                    onClickAccountInformation = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_ACCOUNT_INFORMATION_SCREEN
                        )
                    },
                    onClickNotification = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_NOTIFICATION_SCREEN,
                        )
                    },
                    onClickInquiry = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_INQUIRY_SCREEN,
                        )
                    },
                    onClickDuckieInformation = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_ACCOUNT_INFORMATION_SCREEN -> {
                SettingAccountInformationScreen(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    onClickLogOut = {
                        // TODO
                    },
                    onClickSignOut = {
                        // TODO
                    },
                    accountType = state.accountType,
                    email = state.email,
                )
            }

            SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN -> {
                SettingDuckieInformationScreen(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    onClickTerms = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_TERMS_SCREEN,
                        )
                    },
                    onClickOpenSourceLicense = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_OPEN_SOURCE_LICENSE_SCREEN,
                        )
                    },
                    onClickPrivacyPolicy = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_PRIVACY_POLICY_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_INQUIRY_SCREEN -> {
                SettingInquiryScreen(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_NOTIFICATION_SCREEN -> {
                SettingNotificationScreen(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    activityNotifications = state.activityNotifications,
                    changeActivityNotifications = {
                        launch {
                            settingVM.changeActivityNotifications(it)
                        }
                    },
                    messageNotifications = state.messageNotifications,
                    changeMessageNotifications = {
                        launch {
                            settingVM.changeMessageNotifications(it)
                        }
                    },
                )
            }

            SettingStep.SETTING_TERMS_SCREEN -> {
                SettingTermsScreen(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_OPEN_SOURCE_LICENSE_SCREEN -> {
                SettingLicenseScreen(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_PRIVACY_POLICY_SCREEN -> {
                SettingPrivacyPolicy(
                    onClickBack = {
                        settingVM.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingScreen() {
}
