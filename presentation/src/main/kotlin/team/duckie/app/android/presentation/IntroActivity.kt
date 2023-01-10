/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.OnboardActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.presentation.viewmodel.PresentationViewModel
import team.duckie.app.android.presentation.viewmodel.sideeffect.PresentationSideEffect
import team.duckie.app.android.presentation.viewmodel.state.PresentationState
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

    private val vm: PresentationViewModel by viewModels()
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
            QuackTheme {
                IntroScreen()
            }
        }
    }

    private fun launchOnboardActivity() {
        changeActivityWithAnimation<OnboardActivity>()
    }

    private suspend fun handleState(state: PresentationState) {
        when (state) {
            PresentationState.Initial -> {
                delay(SplashScreenFinishDurationMillis)
                applicationContext.dataStore.data.first().let { preference ->
                    preference[PreferenceKey.Account.AccessToken]?.let { accessToken ->
                        vm.checkAccessToken(accessToken)
                    } ?: launchOnboardActivity()
                }
            }
            PresentationState.AttachedAccessTokenToHeader -> {
                applicationContext.dataStore.data.first().let { preference ->
                    val isOnboardFinsihed = preference[PreferenceKey.Onboard.Finish] ?: false
                    if (isOnboardFinsihed) {
                        toast("온보딩 과거 마침")
                    } else {
                        launchOnboardActivity()
                    }
                }
            }
            PresentationState.AccessTokenValidationFailed -> {
                toast(getString(R.string.expired_access_token_relogin_requried))
                launchOnboardActivity()
            }
            is PresentationState.Error -> {
                state.exception.reportToToast()
            }
        }
    }

    private fun handleSideEffect(sideEffect: PresentationSideEffect) {
        when (sideEffect) {
            is PresentationSideEffect.AttachAccessTokenToHeader -> {
                vm.attachAccessTokenToHeader(sideEffect.accessToken)
            }
            is PresentationSideEffect.ReportError -> {
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }
        }
    }
}
