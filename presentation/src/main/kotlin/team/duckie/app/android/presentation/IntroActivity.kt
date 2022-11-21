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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import team.duckie.app.android.feature.ui.onboard.OnboardActivity
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.changeActivityWithAnimation
import team.duckie.app.android.util.ui.systemBarPaddings
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackImage

private const val DefaultSplashScreenExitAnimationDurationMillis = 200L

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            IntroScreen()

            LaunchedEffect(Unit) {
                delay(2000)
                changeActivityWithAnimation<OnboardActivity>()
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
                    duration = DefaultSplashScreenExitAnimationDurationMillis
                    doOnEnd {
                        splashScreenView.remove()
                    }
                    start()
                }
            }
        }
    }
}

@Composable
private fun IntroScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .padding(systemBarPaddings)
            .padding(
                top = 78.dp,
                bottom = 34.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuackImage(
                src = team.duckie.quackquack.ui.R.drawable.quack_duckie_text_logo,
                size = DpSize(
                    width = 110.dp,
                    height = 32.dp,
                ),
            )
            // TODO: 누락된 typography 컴포넌트 추가
            QuackHeadLine1(
                text = stringResource(R.string.activity_intro_slogan),
            )
        }
        // TODO: SVG 필요
        QuackImage(
            modifier = Modifier.offset(x = 125.dp),
            src = R.drawable.bg_duckie_intro,
            size = DpSize(
                width = 276.dp,
                height = 255.dp,
            ),
        )
    }
}
