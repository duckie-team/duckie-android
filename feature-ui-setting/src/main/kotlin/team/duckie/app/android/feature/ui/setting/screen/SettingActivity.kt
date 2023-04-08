/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import team.duckie.app.android.feature.setting.BuildConfig
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.ui.setting.constans.SettingType
import team.duckie.app.android.feature.ui.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.ui.setting.viewmodel.sideeffect.SettingSideEffect
import team.duckie.app.android.navigator.feature.intro.IntroNavigator
import team.duckie.app.android.shared.ui.compose.DuckieTodoScreen
import team.duckie.app.android.shared.ui.compose.dialog.DuckieDialog
import team.duckie.app.android.shared.ui.compose.dialog.DuckieDialogPosition
import team.duckie.app.android.shared.ui.compose.dialog.duckieDialogPosition
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.app.android.util.ui.startActivityWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.theme.QuackTheme
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
                DuckieDialog(
                    modifier = Modifier.duckieDialogPosition(DuckieDialogPosition.CENTER),
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(QuackColor.White.composeColor)
                        .padding(systemBarPaddings),
                ) {
                    QuackTopAppBar(
                        leadingIcon = QuackIcon.ArrowBack,
                        leadingText = stringResource(id = state.settingType.titleRes),
                        onLeadingIconClick = {
                            vm.navigateBack()
                        },
                    )
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                            ),
                    ) {
                        Crossfade(
                            targetState = state.settingType,
                            animationSpec = QuackAnimationSpec(),
                        ) { step ->
                            when (step) {
                                SettingType.Main -> SettingMainScreen(
                                    vm = vm,
                                    version = BuildConfig.APP_VERSION_NAME,
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
                                SettingType.WithDraw -> SettingWithdrawScreen()
                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
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
        }
    }
}
