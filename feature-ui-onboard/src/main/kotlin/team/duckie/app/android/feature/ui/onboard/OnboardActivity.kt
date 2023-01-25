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
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.di.repository.ProvidesModule
import team.duckie.app.android.di.usecase.kakao.KakaoUseCaseModule
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.home.screen.HomeActivity
import team.duckie.app.android.feature.ui.onboard.constant.CollectInStep
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.ui.onboard.screen.LoginScreen
import team.duckie.app.android.feature.ui.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.ui.onboard.screen.TagScreen
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.android.lifecycle.repeatOnCreated
import team.duckie.app.android.util.android.network.NetworkUtil
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.exception.handling.reporter.reportToToast
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.changeActivityWithAnimation
import team.duckie.app.android.util.ui.collectWithLifecycle
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {

    @Inject
    internal lateinit var onboardVmFactory: OnboardViewModel.OnboardViewModelFactory
    private val vm: OnboardViewModel by viewModels {
        OnboardViewModel.Factory.FactoryProvider(
            factory = onboardVmFactory,
            getKakaoAccessTokenUseCase = getKakaoAccessTokenUseCase,
            owner = this,
        )
    }

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

        onBackPressedDispatcher.addCallback(owner = this) {
            if (onboardStepState == OnboardStep.Login) {
                finishWithAnimation()
            } else {
                val onboardStepState = onboardStepState
                if (onboardStepState != null) {
                    vm.navigateStep(onboardStepState - 1)
                }
            }
        }

        permissionInit()
        vm.observe(
            lifecycleOwner = this,
            state = ::handleState,
            sideEffect = ::handleSideEffect,
        )

        repeatOnCreated {
            launch {
                vm.imagePermissionGrantState.asStateFlow().collectWithLifecycle(lifecycle) { isGranted ->
                    if (isGranted != null) {
                        handleImageStoragePermissionGrantedState(isGranted)
                    }
                }
            }

            launch {
                vm.getCategories(withPopularTags = true)
            }
        }

        setContent {
            QuackTheme {
                QuackAnimatedContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor),
                    targetState = onboardStepState,
                ) { onboardStep ->
                    when (onboardStep) {
                        OnboardStep.Activity, OnboardStep.Login -> LoginScreen()
                        OnboardStep.Profile -> ProfileScreen()
                        OnboardStep.Category -> CategoryScreen()
                        OnboardStep.Tag -> TagScreen()
                        null -> Unit
                    }
                }
            }
        }
    }

    private fun permissionInit() {
        vm.updateImagePermissionGrantState(
            ActivityCompat.checkSelfPermission(
                this,
                vm.imagePermission,
            ) == PackageManager.PERMISSION_GRANTED,
        )
        vm.isCameraPermissionGranted = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED

        if (vm.isImagePermissionGranted == false || !vm.isCameraPermissionGranted) {
            toast(getString(R.string.permission_needs_for_profile_photo))
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun handleImageStoragePermissionGrantedState(isGranted: Boolean) {
        if (isGranted) {
            vm.loadGalleryImages()
        } else {
            toast(getString(R.string.permission_denied))
        }
    }

    private fun handleState(state: OnboardState) {
        onboardStepState = state.step
        if (state.finishOnboarding) {
            vm.finishOnboard(userId = vm.me.id.toString())
        }
    }

    private suspend fun handleSideEffect(sideEffect: OnboardSideEffect) {
        when (sideEffect) {
            is OnboardSideEffect.UpdateGalleryImages -> {
                vm.addGalleryImages(sideEffect.images)
            }
            is OnboardSideEffect.DelegateJoin -> {
                vm.join(sideEffect.kakaoAccessToken)
            }
            is OnboardSideEffect.UpdateAccessToken -> {
                applicationContext.dataStore.edit { preferences ->
                    preferences[PreferenceKey.Account.AccessToken] = sideEffect.accessToken
                }
            }
            is OnboardSideEffect.AttachAccessTokenToHeader -> {
                vm.attachAccessTokenToHeader(sideEffect.accessToken)
            }
            is OnboardSideEffect.Joined -> {
                if (sideEffect.isNewUser) {
                    vm.navigateStep(
                        step = OnboardStep.Profile,
                        ignoreThrottle = true,
                    )
                } else {
                    vm.finishOnboard()
                }
            }
            is OnboardSideEffect.FinishOnboard -> {
                applicationContext.dataStore.edit { preference ->
                    preference[PreferenceKey.Onboard.Finish] = true
                    sideEffect.userId?.let { preference[PreferenceKey.User.Id] = it }
                }
                // TODO(sungbin): 끝낼 때 별다른 메시지를 안하는게 맞을까?
                changeActivityWithAnimation<HomeActivity>()
            }
            is OnboardSideEffect.ReportError -> {
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToToast()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }
            is OnboardSideEffect.NicknameDuplicateChecked -> CollectInStep
        }
    }
}
