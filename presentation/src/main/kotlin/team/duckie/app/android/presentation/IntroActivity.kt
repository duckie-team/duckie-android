/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("PrivatePropertyName")

package team.duckie.app.android.presentation

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.domain.user.model.UserStatus
import team.duckie.app.android.core.datastore.PreferenceKey
import team.duckie.app.android.core.datastore.dataStore
import team.duckie.app.android.feature.home.screen.MainActivity
import team.duckie.app.android.feature.onboard.OnboardActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.presentation.viewmodel.IntroViewModel
import team.duckie.app.android.presentation.viewmodel.sideeffect.IntroSideEffect
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.kotlin.seconds
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.changeActivityWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme

private val SplashScreenExitAnimationDurationMillis = 0.2.seconds
private val SplashScreenFinishDurationMillis = 1.5.seconds

@AndroidEntryPoint
class IntroActivity : BaseActivity() {

    private val vm: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f).run {
                    interpolator = LinearInterpolator()
                    duration = SplashScreenExitAnimationDurationMillis
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }

        vm.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )

        setContent {
            LaunchedEffect(vm) {
                delay(SplashScreenFinishDurationMillis)
                vm.checkUpdateRequire()
            }

            QuackTheme {
                IntroScreen()
            }
        }
    }

    private suspend fun handleSideEffect(sideEffect: IntroSideEffect) {
        when (sideEffect) {
            is IntroSideEffect.GetUserFinished -> {
                val preference = applicationContext.dataStore.data.first()
                val isOnboardFinished = preference[PreferenceKey.Onboard.Finish]
                if (isOnboardFinished == null || sideEffect.user.status == UserStatus.NEW) {
                    launchOnboardActivity()
                } else {
                    launchHomeOrOnboardActivity(isOnboardFinished)
                }
            }

            is IntroSideEffect.UserNotInitialized -> {
                launchOnboardActivity()
            }

            is IntroSideEffect.GetMeError -> {
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
                launchOnboardActivity()
            }

            is IntroSideEffect.ReportError -> {
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }
        }
    }

    private fun launchOnboardActivity() {
        changeActivityWithAnimation<OnboardActivity>()
    }

    private fun launchHomeActivity() {
        changeActivityWithAnimation<MainActivity>()
    }

    private fun launchHomeOrOnboardActivity(isOnboardFinish: Boolean) {
        if (isOnboardFinish) {
            launchHomeActivity()
        } else {
            launchOnboardActivity()
        }
    }
}
