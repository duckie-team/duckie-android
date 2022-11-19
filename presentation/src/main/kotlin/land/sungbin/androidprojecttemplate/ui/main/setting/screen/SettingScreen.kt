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
                SettingSideEffect.FetchSetting -> {
                }
                SettingSideEffect.PostSetting -> {
                }
                SettingSideEffect.FetchAccountInformation -> {
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
            SettingStep.SettingMainScreen -> SettingMainScreen(
                onClickBack = {
                },
                onClickAccountInformation = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingAccountInformationScreen
                    )
                },
                onClickNotification = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingNotificationScreen,
                    )
                },
                onClickInquiry = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingInquiryScreen,
                    )
                },
                onClickDuckieInformation = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingDuckieInformationScreen,
                    )
                },
            )

            SettingStep.SettingAccountInformationScreen -> SettingAccountInformationScreen(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingMainScreen,
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

            SettingStep.SettingDuckieInformationScreen -> SettingDuckieInformationScreen(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingMainScreen,
                    )
                },
                onClickTerms = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingTermsScreen,
                    )
                },
                onClickOpenSourceLicense = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingOpenSourceLicenseScreen,
                    )
                },
                onClickPrivacyPolicy = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingPrivacyPolicyScreen,
                    )
                },
            )

            SettingStep.SettingInquiryScreen -> SettingInquiryScreen(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingMainScreen,
                    )
                },
            )

            SettingStep.SettingNotificationScreen -> SettingNotificationScreen(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingMainScreen,
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

            SettingStep.SettingTermsScreen -> SettingTermsScreen(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingDuckieInformationScreen,
                    )
                },
            )

            SettingStep.SettingOpenSourceLicenseScreen -> SettingLicenseScreen(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingDuckieInformationScreen,
                    )
                },
            )

            SettingStep.SettingPrivacyPolicyScreen -> SettingPrivacyPolicy(
                onClickBack = {
                    settingVM.navigatePage(
                        step = SettingStep.SettingDuckieInformationScreen,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingScreen() {
}
