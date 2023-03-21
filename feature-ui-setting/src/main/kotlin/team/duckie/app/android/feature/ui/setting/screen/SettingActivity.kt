/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.screen

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
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.setting.constans.SettingType
import team.duckie.app.android.feature.ui.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.ui.setting.viewmodel.sideeffect.SettingSideEffect
import team.duckie.app.android.shared.ui.compose.DuckieTodoScreen
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

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
                        Crossfade(targetState = state.settingType) { step ->
                            when (step) {
                                SettingType.Main -> SettingMainScreen(
                                    vm = vm,
                                    version = "1.0.0-MVP", // TODO(limsaehyun): 버전 코드 가져오기
                                )

                                SettingType.AccountInfo -> AccountInfoScreen(state = state)
                                SettingType.Notification -> DuckieTodoScreen()
                                SettingType.Inquiry -> DuckieTodoScreen()
                                SettingType.TermsAndPolicies -> DuckieTodoScreen()
                                SettingType.Version -> DuckieTodoScreen()
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
        }
    }
}
