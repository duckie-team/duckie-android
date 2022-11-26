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
import android.view.animation.AnticipateInterpolator
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import team.duckie.app.android.feature.ui.onboard.OnboardActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.changeActivityWithAnimation

private val SplashScreenExitAnimationDurationMillis = 0.2.seconds
private val SplashScreenFinishDurationMillis = 2.seconds

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(Unit) {
                delay(SplashScreenFinishDurationMillis)
                changeActivityWithAnimation<OnboardActivity>()
            }

            IntroScreen()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    1f,
                    0f,
                ).run {
                    interpolator = AnticipateInterpolator()
                    duration = SplashScreenExitAnimationDurationMillis
                    doOnEnd {
                        splashScreenView.remove()
                    }
                    start()
                }
            }
        }
    }
}

