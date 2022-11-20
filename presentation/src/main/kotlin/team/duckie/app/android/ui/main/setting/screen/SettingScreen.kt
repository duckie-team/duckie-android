package team.duckie.app.android.ui.main.setting.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import team.duckie.app.shared.compose.extension.CoroutineScopeContent
import team.duckie.app.shared.compose.extension.launch
import team.duckie.app.ui.component.startActivityWithAnimation
import team.duckie.app.ui.main.setting.mvi.SettingSideEffect
import team.duckie.app.ui.main.setting.utils.SettingStep
import team.duckie.app.ui.main.setting.vm.SettingViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun SettingScreen(
    settingVM: SettingViewModel,
) = CoroutineScopeContent {

    val state = settingVM.state.collectAsStateWithLifecycle().value
    val effect = settingVM.effect
    val currentActivity = LocalContext.current as Activity

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
                    currentActivity.startActivityWithAnimation {
                        Intent(
                            currentActivity,
                            OssLicensesMenuActivity::class.java,
                        )
                    }
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
