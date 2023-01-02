/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import team.duckie.app.android.di.repository.ProvidesModule
import team.duckie.app.android.di.usecase.kakao.KakaoUseCaseModule
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.ui.onboard.screen.LoginScreen
import team.duckie.app.android.feature.ui.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.ui.onboard.screen.TagScreen
import team.duckie.app.android.feature.ui.onboard.util.NetworkUtil
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.compose.setDuckieContent
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.collectWithLifecycle
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {

    @Inject
    internal lateinit var vmFactory: OnboardViewModel.ViewModelFactory
    private lateinit var vm: OnboardViewModel

    private val kakaoRepository by lazy {
        ProvidesModule.provideKakaoRepository(activityContext = this)
    }
    private val getKakaoAccessTokenUseCase by lazy {
        KakaoUseCaseModule.provideGetKakaoAccessTokenUseCase(repository = kakaoRepository)
    }

    private val toast by lazy { ToastWrapper(applicationContext) }
    private var onboardStepState by mutableStateOf<OnboardStep?>(null)

    private val permissions by lazy {
        arrayOf(
            vm.imagePermission,
            Manifest.permission.CAMERA,
        )
    }
    private val requestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
            vm.updateImagePermissionGrantState(isGranted[vm.imagePermission])
            vm.isCameraPermissionGranted = isGranted[Manifest.permission.CAMERA] ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!NetworkUtil.isNetworkAvailable(applicationContext)) {
            toast(getString(R.string.bad_internet))
            return finishWithAnimation()
        }

        vm = vmFactory.create(getKakaoAccessTokenUseCase = getKakaoAccessTokenUseCase)
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

        permissionInit()

        // FIXME: Lifecycle 처리 개선 (성빈의 지식 부족)
        lifecycleScope.launchWhenCreated {
            launch {
                vm.imagePermissionGrantState.collectWithLifecycle(lifecycle) { isGranted ->
                    if (isGranted != null) {
                        handleImageStoragePermissionGrantedState(isGranted)
                    }
                }
            }

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
                    null -> Unit
                }
            }
        }
    }

    private fun permissionInit() {
        vm.updateImagePermissionGrantState(
            ActivityCompat.checkSelfPermission(
                this,
                vm.imagePermission
            ) == PackageManager.PERMISSION_GRANTED
        )
        vm.isCameraPermissionGranted = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (vm.isImagePermissionGranted == false || !vm.isCameraPermissionGranted) {
            toast(getString(R.string.permission_needs_for_profile_photo))
            requestPermissionLauncher.launch(permissions)
        }
    }

    private suspend fun handleImageStoragePermissionGrantedState(isGranted: Boolean) {
        if (isGranted) {
            vm.loadGalleryImages()
        } else {
            toast(getString(R.string.permission_denied))
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
            is OnboardState.Joined -> Unit // TODO(sungbin): 기능 연결
            is OnboardState.NavigateStep -> {
                vm.navigateStep(state.step)
                onboardStepState = state.step
            }
            OnboardState.AccessTokenValidationFailed -> Unit // TODO(sungbin): 기능 연결
            is OnboardState.CategoriesLoaded -> Unit // TODO(sungbin): 기능 연결
            is OnboardState.FileUploaded -> Unit // TODO(sungbin): 기능 연결
            is OnboardState.TagCreated -> Unit // TODO(sungbin): 기능 연결
            is OnboardState.Error -> {
                toast(getString(R.string.internal_error))
            }
        }
    }

    private suspend fun handleSideEffect(effect: OnboardSideEffect) {
        when (effect) {
            is OnboardSideEffect.UpdateGalleryImages -> {
                vm.addGalleryImages(effect.images)
            }
            is OnboardSideEffect.UpdateUser -> {
                vm.me = effect.user
            }
            is OnboardSideEffect.UpdateAccessToken -> {
                applicationContext.dataStore.edit { preferences ->
                    preferences[PreferenceKey.Account.AccessToken] = effect.accessToken
                }
            }
            is OnboardSideEffect.AttachAccessTokenToHeader -> Unit // TODO(sungbin): 기능 연결
            is OnboardSideEffect.DelegateJoin -> {
                vm.join(effect.kakaoAccessToken)
            }
            is OnboardSideEffect.ReportError -> {
                effect.exception.printStackTrace()
                Firebase.crashlytics.recordException(effect.exception)
            }
        }
    }
}
