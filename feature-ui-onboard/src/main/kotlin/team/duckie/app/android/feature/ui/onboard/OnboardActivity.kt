/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import team.duckie.app.android.di.repository.UserRepositoryModule
import team.duckie.app.android.di.usecase.user.UserUseCaseModule
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.constaint.OnboardStep
import team.duckie.app.android.feature.ui.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.ui.onboard.screen.LoginScreen
import team.duckie.app.android.feature.ui.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.ui.onboard.screen.TagScreen
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: OnboardViewModel.ViewModelFactory

    private lateinit var vm: OnboardViewModel

    private val userRepository by lazy {
        UserRepositoryModule.provideKakaoLoginRepository(
            activityContext = this
        )
    }
    private val kakaoLoginUseCase by lazy {
        UserUseCaseModule.provideKakaoLoginUseCase(
            userRepository
        )
    }

    private val toast by lazy { ToastWrapper(applicationContext) }
    private var onboardStepState by mutableStateOf<OnboardStep?>(null)

    private fun setupViewModel() {
        vm = viewModelFactory.create(kakaoLoginUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        onBackPressedDispatcher.addCallback(owner = this) {
            if (onboardStepState == OnboardStep.Login || onboardStepState == OnboardStep.Tag) {
                finishWithAnimation()
            } else {
                val onboardStepState = onboardStepState
                if (onboardStepState != null) {
                    vm.navigateStep(onboardStepState - 1)
                }
            }
        }

        // FIXME: Lifecycle 처리 개선 (성빈의 지식 부족)
        lifecycleScope.launchWhenCreated {
            launch {
                vm.loadGalleryImages()
            }

            launch {
                vm.state
                    .flowWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.CREATED,
                    )
                    .collect(::handleState)
            }

            launch {
                vm.sideEffect
                    .flowWithLifecycle(
                        lifecycle = lifecycle,
                        minActiveState = Lifecycle.State.CREATED,
                    )
                    .collect(::handleSideEffect)
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
                        when (onboardStep) {
                            OnboardStep.Login -> LoginScreen()
                            OnboardStep.Profile -> ProfileScreen()
                            OnboardStep.Category -> CategoryScreen()
                            OnboardStep.Tag -> TagScreen()
                            else -> Unit // onboardStep is null
                        }
                    }
                }
            }
        }
    }

    private fun handleState(state: OnboardState) {
        when (state) {
            OnboardState.Initial -> {
                vm.navigateStep(
                    step = OnboardStep.Login,
                    ignoreThrottle = true,
                )
            }

            is OnboardState.NavigateStep -> {
                vm.navigateStep(state.step)
                onboardStepState = state.step
            }

            is OnboardState.Error -> {
                toast(getString(R.string.internal_error))
                state.exception.printStackTrace()
            }
        }
    }

    private suspend fun handleSideEffect(sideEffect: OnboardSideEffect) {
        when (sideEffect) {
            is OnboardSideEffect.SaveUser -> {
                vm.me = sideEffect.user
                dataStore.edit { preference ->
                    val (nickname, profileImage, email) = sideEffect.user
                    preference[PreferenceKey.User.Nickname] = nickname
                    preference[PreferenceKey.User.ProfilePhoto] = profileImage
                    email?.let { preference[PreferenceKey.User.Email] = email }
                }
            }

            is OnboardSideEffect.UpdateGalleryImages -> {
                vm.addGalleryImages(sideEffect.images)
            }

            is OnboardSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }
        }
    }
}
