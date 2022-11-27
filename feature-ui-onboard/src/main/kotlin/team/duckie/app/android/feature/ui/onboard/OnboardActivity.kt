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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import team.duckie.app.android.di.repository.GalleryRepositoryModule
import team.duckie.app.android.di.repository.UserRepositoryModule
import team.duckie.app.android.di.usecase.gallery.GalleryUseCaseModule
import team.duckie.app.android.di.usecase.user.UserUseCaseModule
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.ui.onboard.screen.LoginScreen
import team.duckie.app.android.feature.ui.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.ui.onboard.screen.TagScreen
import team.duckie.app.android.feature.ui.onboard.screen.galleryImages
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.constaint.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

class OnboardActivity : BaseActivity() {
    // FIXME: hilt 가 작동을 안함... 성빈의 hilt 숙련도가 다 죽은걸로 ㅠ
    private val userRepository by lazy { UserRepositoryModule.provideKakaoLoginRepository(activityContext = this) }
    private val kakaoLoginUseCase by lazy { UserUseCaseModule.provideKakaoLoginUseCase(userRepository) }

    private val galleryRepository by lazy { GalleryRepositoryModule.provideGalleryRepository(applicationContext) }
    private val loadGalleryImagesUseCase by lazy { GalleryUseCaseModule.provideGalleryUseCase(galleryRepository) }

    private val vm by lazy {
        OnboardViewModel(
            kakaoLoginUseCase = kakaoLoginUseCase,
            loadGalleryImagesUseCase = loadGalleryImagesUseCase,
        )
    }

    private val toast by lazy { ToastWrapper(applicationContext) }
    private var onboardStepState by mutableStateOf<OnboardStep?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(owner = this) {
            if (onboardStepState == OnboardStep.Login) {
                finishWithAnimation()
            } else {
                val onboardStepState = onboardStepState
                if (onboardStepState != null) {
                    vm.updateStep(onboardStepState - 1)
                }
            }
        }

        // FIXME: Lifecycle 처리 개선 (성빈의 지식 부족)
        lifecycleScope.launchWhenCreated {
            launch {
                vm.state
                    .flowWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.CREATED,
                    )
                    .collect { state ->
                        when (state) {
                            OnboardState.Initial -> {
                                vm.updateStep(
                                    step = OnboardStep.Login,
                                    ignoreThrottle = true,
                                )
                            }
                            is OnboardState.NavigateStep -> {
                                vm.updateStep(state.step)
                                onboardStepState = state.step
                            }
                            is OnboardState.GalleryImageLoaded -> {
                                galleryImages.value = state.images
                            }
                            is OnboardState.Error -> {
                                toast(getString(R.string.internal_error))
                                state.exception.printStackTrace()
                            }
                        }
                    }
            }

            launch {
                vm.sideEffect
                    .flowWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.CREATED,
                    )
                    .collect { sideEffect ->
                        when (sideEffect) {
                            is OnboardSideEffect.SaveUser -> {
                                dataStore.edit { preference ->
                                    val (nickname, profileImage, email) = sideEffect.user
                                    preference[PreferenceKey.User.Nickname] = nickname
                                    preference[PreferenceKey.User.ProfilePhoto] = profileImage
                                    email?.let { preference[PreferenceKey.User.Email] = email }
                                }
                            }
                            is OnboardSideEffect.ReportError -> {
                                Firebase.crashlytics.recordException(sideEffect.exception)
                            }
                        }
                    }
            }
        }

        setContent {
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
                                val onboardStepState = onboardStepState
                                if (onboardStep != OnboardStep.Tag && onboardStepState != null) {
                                    OnboardTopAppBar(
                                        currentStep = onboardStepState,
                                        showSkipTrailingText = false,
                                    )
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
