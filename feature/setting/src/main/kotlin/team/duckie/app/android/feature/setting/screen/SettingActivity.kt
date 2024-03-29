/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.intent.goToMarket
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.common.compose.ui.DuckieTodoScreen
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.feature.dev.mode.DevModeDialog
import team.duckie.app.android.feature.setting.BuildConfig
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.setting.viewmodel.sideeffect.SettingSideEffect
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState
import team.duckie.app.android.navigator.feature.intro.IntroNavigator
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

    @Inject
    lateinit var introNavigator: IntroNavigator

    private val vm: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(key1 = vm) {
                vm.container.sideEffectFlow
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            BackHandler {
                vm.navigateBack()
            }

            val state = vm.collectAsState().value

            QuackTheme {
                SettingDialog(state = state)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(QuackColor.White.value)
                        .padding(systemBarPaddings),
                ) {
                    BackPressedHeadLine2TopAppBar(title = stringResource(id = state.settingType.titleRes)) {
                        vm.navigateBack()
                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                            ),
                    ) {
                        QuackCrossfade(
                            targetState = state.settingType,
                        ) { step ->
                            when (step) {
                                SettingType.Main -> SettingMainScreen(
                                    vm = vm,
                                    version = if (state.isStage) {
                                        "${BuildConfig.APP_VERSION_NAME}.${BuildConfig.APP_VERSION_CODE}"
                                    } else {
                                        BuildConfig.APP_VERSION_NAME
                                    },
                                )

                                SettingType.AccountInfo -> SettingAccountInfoScreen(
                                    email = "", // TODO(limsaehyun) : 이메일 가져오기 & OAuth 정보 넘겨주기
                                    onClickLogOut = {
                                        vm.changeLogoutDialogVisible(true)
                                    },
                                    onClickWithdraw = {
                                        vm.navigateStep(SettingType.WithDraw)
                                    },
                                )

                                SettingType.Notification -> DuckieTodoScreen() // TODO(limsaehyun): SettingNotificationScreen 사용 필요
                                SettingType.Inquiry -> SettingInquiryScreen()
                                SettingType.MainPolicy -> SettingMainPolicyScreen(
                                    navigatePage = {
                                        vm.navigateStep(it)
                                    },
                                    navigateOssLicense = {
                                        vm.navigateOssLicense()
                                    },
                                )

                                SettingType.PrivacyPolicy -> SettingPrivacyPolicy()
                                SettingType.TermsOfService -> SettingTermsOfServiceScreen()
                                SettingType.WithDraw -> SettingWithdrawScreen(
                                    vm = vm,
                                    state = state,
                                )

                                SettingType.ListOfIgnoreUser -> SettingIgnoreUserScreen(
                                    state = state,
                                    vm = vm,
                                )

                                SettingType.ListOfIgnoreExam -> SettingIgnoreExamScreen(
                                    vm = vm,
                                    state = state,
                                )

                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SettingDialog(
        state: SettingState,
    ) {
        // 로그아웃 확인 목적의 Dialog
        DuckieDialog(
            title = stringResource(id = R.string.log_out_check_message),
            leftButtonText = stringResource(id = R.string.cancel),
            leftButtonOnClick = {
                vm.changeLogoutDialogVisible(false)
            },
            rightButtonText = stringResource(id = R.string.log_out),
            rightButtonOnClick = {
                vm.logout()
            },
            visible = state.logoutDialogVisible,
            onDismissRequest = {
                vm.changeLogoutDialogVisible(false)
            },
        )

        // 회원탈퇴 확인 목적의 Dialog
        DuckieDialog(
            title = stringResource(id = R.string.withdraw_check_message),
            leftButtonText = stringResource(id = R.string.withdraw_cancel_msg),
            leftButtonOnClick = {
                vm.changeWithdrawDialogVisible(false)
            },
            rightButtonText = stringResource(id = R.string.withdraw),
            rightButtonOnClick = vm::withdraw,
            visible = state.withdrawDialogVisible,
            onDismissRequest = {
                vm.changeWithdrawDialogVisible(false)
            },
        )

        // 개발자 모드 Dialog
        DevModeDialog(
            visible = state.devModeDialogVisible,
            onDismiss = {
                vm.changeDevModeDialogVisible(false)
            },
        )
    }

    private fun handleSideEffect(sideEffect: SettingSideEffect) {
        when (sideEffect) {
            is SettingSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            is SettingSideEffect.NavigateBack -> {
                finishWithAnimation()
            }

            is SettingSideEffect.NavigateOssLicense -> {
                startActivityWithAnimation<OssLicensesMenuActivity>()
            }

            is SettingSideEffect.NavigateIntro -> {
                introNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    },
                )
            }

            is SettingSideEffect.NavigatePlayStoreMarket -> {
                goToMarket()
            }

            is SettingSideEffect.ShowToast -> {
                ToastWrapper(context = applicationContext).invoke(sideEffect.message)
            }
        }
    }
}
