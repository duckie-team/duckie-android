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
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.home.screen.HomeActivity
import team.duckie.app.android.feature.ui.onboard.OnboardActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.presentation.viewmodel.IntroViewModel
import team.duckie.app.android.presentation.viewmodel.sideeffect.IntroSideEffect
import team.duckie.app.android.presentation.viewmodel.state.IntroState
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.exception.handling.reporter.reportToToast
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.changeActivityWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme

private val SplashScreenExitAnimationDurationMillis = 0.2.seconds
private val SplashScreenFinishDurationMillis = 1.5.seconds

@AndroidEntryPoint
class IntroActivity : BaseActivity() {

    private val vm: IntroViewModel by viewModels()
    private val toast by lazy { ToastWrapper(applicationContext) }

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
            state = ::handleState,
            sideEffect = ::handleSideEffect,
        )

        setContent {
            LaunchedEffect(vm) {
                delay(SplashScreenFinishDurationMillis)
                applicationContext.dataStore.data.first().let { preference ->
                    preference[PreferenceKey.Account.AccessToken]?.let { accessToken ->
                        vm.attachAccessTokenToHeaderIfValidationPassed(accessToken)
                    } ?: launchOnboardActivity()
                }
            }

            QuackTheme {
                IntroScreen()
            }
        }
    }

    private suspend fun handleState(state: IntroState) {
        with(state) {
            when {
                accessTokenAttachedToHeader -> {
                    applicationContext.dataStore.data.first().let { preference ->
                        val isOnboardFinsihed = preference[PreferenceKey.Onboard.Finish] ?: false
                        if (isOnboardFinsihed) {
                            launchHomeActivity()
                        } else {
                            launchOnboardActivity()
                        }
                    }
                }
                accessTokenValidationFail == true -> {
                    toast(getString(R.string.expired_access_token_relogin_requried))
                    launchOnboardActivity()
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: IntroSideEffect) {
        when (sideEffect) {
            is IntroSideEffect.AttachAccessTokenToHeader -> {
                vm.attachAccessTokenToHeader(sideEffect.accessToken)
            }
            is IntroSideEffect.ReportError -> {
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToToast()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }
        }
    }

    private fun launchOnboardActivity() {
        changeActivityWithAnimation<OnboardActivity>()
    }

    private fun launchHomeActivity() {
        // TODO(sungbin): 끝낼 때 별다른 메시지를 안하는게 맞을까?
        changeActivityWithAnimation<HomeActivity>()
    }
}
