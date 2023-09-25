/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("PrivatePropertyName")

package team.duckie.app.android.presentation

import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.deeplink.DynamicLinkHelper
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import team.duckie.app.android.common.kotlin.seconds
import team.duckie.app.android.core.datastore.PreferenceKey
import team.duckie.app.android.core.datastore.dataStore
import team.duckie.app.android.domain.user.model.UserStatus
import team.duckie.app.android.feature.home.screen.MainActivity
import team.duckie.app.android.feature.onboard.OnboardActivity
import team.duckie.app.android.presentation.screen.IntroScreen
import team.duckie.app.android.presentation.viewmodel.IntroViewModel
import team.duckie.app.android.presentation.viewmodel.sideeffect.IntroSideEffect
import team.duckie.quackquack.material.theme.QuackTheme

private val SplashScreenExitAnimationDurationMillis = 0.2.seconds
private val LottieDurationMillis = 2.seconds

@AndroidEntryPoint
class IntroActivity : BaseActivity() {

    private val vm: IntroViewModel by viewModels()
    private val enabledDelay get() = isTaskRoot

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (enabledDelay) {
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
        }

        vm.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )

        setContent {
            LaunchedEffect(vm) {
                if (enabledDelay) {
                    delay(LottieDurationMillis)
                }
                vm.checkUpdateRequire()
            }

            QuackTheme {
                IntroScreen()
            }
        }
    }

    private fun parseExamIdDeepLink(): Deferred<Int?> {
        val dynamicLink = CompletableDeferred<Int?>()

        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                val examId = pendingDynamicLinkData?.link?.let { deepLink ->
                    DynamicLinkHelper.parseExamId(deepLink)
                }?.toIntOrNull()
                dynamicLink.complete(examId)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "getDynamicLink:onFailure", exception)
                dynamicLink.complete(null)
            }

        return dynamicLink
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

    private fun launchOnboardActivity() = lifecycleScope.launch {
        val dynamicLinkExamId = parseExamIdDeepLink().await()
        startActivityWithAnimation<OnboardActivity>(
            intentBuilder = {
                if (dynamicLinkExamId != null) putExtra(Extras.DynamicLinkExamId, dynamicLinkExamId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            },
        )
    }

    private fun launchHomeActivity() = lifecycleScope.launch {
        val dynamicLinkExamId = parseExamIdDeepLink().await()
        startActivityWithAnimation<MainActivity>(
            intentBuilder = {
                if (dynamicLinkExamId != null) putExtra(Extras.DynamicLinkExamId, dynamicLinkExamId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            },
        )
    }

    private fun launchHomeOrOnboardActivity(isOnboardFinish: Boolean) {
        if (isOnboardFinish) {
            launchHomeActivity()
        } else {
            launchOnboardActivity()
        }
    }
}
