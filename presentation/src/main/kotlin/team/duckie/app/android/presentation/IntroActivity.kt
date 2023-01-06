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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.home.screen.HomeActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.changeActivityWithAnimation

private val SplashScreenExitAnimationDurationMillis = 0.2.seconds
private val SplashScreenFinishDurationMillis = 2.seconds

class IntroActivity : BaseActivity() {
    private val toast by lazy { ToastWrapper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            var isOnboardFinished by remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                delay(SplashScreenFinishDurationMillis)
                applicationContext.dataStore.data.first().let { preference ->
                    isOnboardFinished = preference[PreferenceKey.Onboard.Finish] ?: false
                }
            }

            IntroScreen()

            when (isOnboardFinished) {
                true -> toast("온보딩 끝냄")
                false -> changeActivityWithAnimation<HomeActivity>()
                else -> Unit // null
            }
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
