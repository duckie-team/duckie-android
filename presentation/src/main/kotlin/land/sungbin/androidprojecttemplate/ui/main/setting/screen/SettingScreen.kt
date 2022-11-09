package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep
import land.sungbin.androidprojecttemplate.ui.main.setting.vm.SettingViewModel

@Composable
internal fun SettingScreen(
    vm: SettingViewModel,
) {
    Crossfade(
        targetState = vm.currentStep.collectAsState().value
    ) { page ->
        when (page) {
            SettingStep.SETTING_MAIN_SCREEN -> {
                SettingMainScreen(
                    onClickBack = {
                        //처TODO
                    },
                    onClickAccountInformation = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_ACCOUNT_INFORMATION_SCREEN,
                        )
                    },
                    onClickNotification = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_NOTIFICATION_SCREEN
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
                        //TODO
                    },
                    onClickPrivacyPolicy = {
                        //TODO
                    },
                )
            }

            SettingStep.SETTING_INQUIRY_SCREEN -> {
                SettingInquiryScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    }
                )
            }

            SettingStep.SETTING_NOTIFICATION_SCREEN -> {
                SettingNotificationScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_MAIN_SCREEN,
                        )
                    }
                )
            }

            SettingStep.SETTING_TERMS_SCREEN -> {
                SettingTermsScreen(
                    onClickBack = {
                        vm.navigatePage(
                            step = SettingStep.SETTING_DUCKIE_INFORMATION_SCREEN,
                        )
                    }
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