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
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.OnboardActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.presentation.viewmodel.PresentationViewModel
import team.duckie.app.android.presentation.viewmodel.sideeffect.PresentationSideEffect
import team.duckie.app.android.presentation.viewmodel.state.PresentationState
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.compose.setDuckieContent
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlytics
import team.duckie.app.android.util.exception.handling.reporter.reportToToast
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.changeActivityWithAnimation
import team.duckie.app.android.util.ui.collectWithLifecycle

private val SplashScreenExitAnimationDurationMillis = 0.2.seconds
private val SplashScreenFinishDurationMillis = 1.5.seconds

@AndroidEntryPoint
class IntroActivity : BaseActivity() {

    @Inject
    internal lateinit var vm: PresentationViewModel
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

        // TODO(sungbin): Lifecycle 처리 개선
        lifecycleScope.launchWhenCreated {
            launch {
                vm.state.collectWithLifecycle(
                    lifecycle = lifecycle,
                    collector = ::handleState,
                )
            }

            launch {
                vm.sideEffect.collectWithLifecycle(
                    lifecycle = lifecycle,
                    collector = ::handleSideEffect,
                )
            }
        }

        setDuckieContent(viewmodel = vm) {
            IntroScreen()
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

    private suspend fun handleSideEffect(effect: PresentationSideEffect) {
        when (effect) {
            is PresentationSideEffect.AttachAccessTokenToHeader -> {
                vm.attachAccessTokenToHeader(effect.accessToken)
            }
            is PresentationSideEffect.ReportError -> {
                effect.exception.printStackTrace()
                effect.exception.reportToCrashlytics()
            }
        }
    }
}
