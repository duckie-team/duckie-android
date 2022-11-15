package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import land.sungbin.androidprojecttemplate.shared.compose.extension.CoroutineScopeContent
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep
import land.sungbin.androidprojecttemplate.ui.main.setting.vm.SettingViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun SettingScreen(
    vm: SettingViewModel,
) = CoroutineScopeContent {

    val state = vm.state.collectAsStateWithLifecycle().value

    Crossfade(
        targetState = state.currentStep
    ) { page ->
        when (page) {
            SettingStep.SETTING_MAIN_SCREEN -> {
                SettingMainScreen(
                    onClickBack = {

                    },
                    onClickAccountInformation = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_NOTIFICATION_SCREEN
                        )
                    },
                    onClickNotification = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_NOTIFICATION_SCREEN,
                        )
                    },
                    onClickInquiry = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_INQUIRY_SCREEN,
                        )
                    },
                    onClickDuckieInformation = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_ACCOUNT_INFORMATION_SCREEN -> {
                SettingAccountInformationScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    onClickLogOut = {
                        //TODO
                    },
                    onClickSignOut = {
                        //TODO
                    },
                    accountType = state.accountType,
                    email = state.email,
                )
            }

            SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN -> {
                SettingDuckieInformationScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    onClickTerms = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_TERMS_SCREEN,
                        )
                    },
                    onClickOpenSourceLicense = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_OPEN_SOURCE_LICENSE_SCREEN,
                        )
                    },
                    onClickPrivacyPolicy = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_PRIVACY_POLICY_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_INQUIRY_SCREEN -> {
                SettingInquiryScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    email = state.email,
                    instagram = state.instagram,
                )
            }

            SettingStep.SETTING_NOTIFICATION_SCREEN -> {
                SettingNotificationScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    },
                    activityNotifications = state.activityNotifications,
                    changeActivityNotifications = {
                        vm.changeActivityNotifications(it)
                    },
                    messageNotifications = state.messageNotifications,
                    changeMessageNotifications = {
                        vm.changeMessageNotifications(it)
                    },
                )
            }

            SettingStep.SETTING_TERMS_SCREEN -> {
                SettingTermsScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_OPEN_SOURCE_LICENSE_SCREEN -> {
                SettingLicenseScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    },
                )
            }

            SettingStep.SETTING_PRIVACY_POLICY_SCREEN -> {
                SettingPrivacyPolicy(
                    onClickBack = {
                        vm.navigatePage(
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
    SettingScreen(vm = SettingViewModel())
}