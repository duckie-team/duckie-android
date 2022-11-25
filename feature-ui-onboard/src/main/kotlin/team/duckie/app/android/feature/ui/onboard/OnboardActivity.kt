/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.onboard

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.ui.onboard.screen.LoginScreen
import team.duckie.app.android.feature.ui.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.ui.onboard.screen.TagScreen
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

class OnboardActivity : BaseActivity() {
    private val vm = OnboardViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(owner = this) {
            if (vm.step.value == OnboardStep.Login) {
                finish()
                overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                )
            } else {
                vm.updateStep(vm.currentStep - 1)
            }
        }

        setContent {
            val onboardStepState by vm.step.collectAsStateWithLifecycle()

            QuackTheme {
                CompositionLocalProvider(LocalViewModel provides vm) {
                    QuackAnimatedContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = QuackColor.White.composeColor),
                        targetState = onboardStepState,
                    ) { onboardStep ->
                        if (onboardStep == OnboardStep.Login) {
                            LoginScreen()
                        } else {
                            Column(modifier = Modifier.fillMaxSize()) {
                                // Tag 단계일 때는 WindowInsets 에 대한 별도 처리가 필요함
                                // (ModalBottomSheet 의 dimmed 가 status bar 까지 적용돼야 함)
                                if (onboardStep != OnboardStep.Tag) {
                                    OnboardTopAppBar(showSkipTrailingText = false)
                                }
                                when (onboardStep) {
                                    OnboardStep.Profile -> ProfileScreen()
                                    OnboardStep.Category -> CategoryScreen()
                                    OnboardStep.Tag -> TagScreen()
                                    else -> Unit // unreached
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
