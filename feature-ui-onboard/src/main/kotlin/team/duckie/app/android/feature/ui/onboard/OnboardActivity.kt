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
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import team.duckie.app.android.di.repository.UserRepositoryModule
import team.duckie.app.android.di.usecase.user.UserUseCaseModule
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.ui.onboard.screen.LoginScreen
import team.duckie.app.android.feature.ui.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.ui.onboard.screen.TagScreen
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.KakaoUserSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.KakaoLoginState
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

class OnboardActivity : BaseActivity() {
    // FIXME: hilt 가 작동을 안함... 성빈의 hilt 숙련도가 다 죽은걸로 ㅠ
    private val repository by lazy {
        UserRepositoryModule.provideKakaoLoginRepository(activityContext = this)
    }
    private val kakaoLoginUseCase by lazy {
        UserUseCaseModule.provideKakaoLoginUseCase(repository)
    }
    private val vm by lazy {
        OnboardViewModel(kakaoLoginUseCase = kakaoLoginUseCase)
    }
    private val toast by lazy {
        ToastWrapper(applicationContext)
    }

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

        // TODO: Lifecycle 처리 개선 (성빈의 지식 부족)
        lifecycleScope.launchWhenCreated {
            launch {
                vm.loginState
                    .flowWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.CREATED,
                    )
                    .collect { loginState ->
                        when (loginState) {
                            KakaoLoginState.Initial -> Unit
                            is KakaoLoginState.Success -> {
                                vm.updateStep(vm.currentStep + 1)
                            }
                            is KakaoLoginState.Error -> {
                                toast(getString(R.string.kakaologin_fail))
                                // TODO: DebugLog.exception(exception) 개발
                                // DebugLog 는 오직 build type 이 Debug 일 때만 작동해야 함
                                loginState.exception.printStackTrace()
                            }
                        }
                    }
            }

            launch {
                vm.kakaoUserSideEffect
                    .flowWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.CREATED,
                    )
                    .collect { sideEffect ->
                        when (sideEffect) {
                            is KakaoUserSideEffect.Save -> {
                                dataStore.edit { preference ->
                                    val (nickname, profileImage, email) = sideEffect.user
                                    preference[PreferenceKey.User.Nickname] = nickname
                                    preference[PreferenceKey.User.ProfilePhoto] = profileImage
                                    email?.let {
                                        preference[PreferenceKey.User.Email] = email
                                    }
                                }
                            }
                        }
                    }
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
