/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.onboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.exception.handling.reporter.reportToToast
import team.duckie.app.android.common.android.lifecycle.repeatOnCreated
import team.duckie.app.android.common.android.network.NetworkUtil
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.changeActivityWithAnimation
import team.duckie.app.android.common.android.ui.collectWithLifecycle
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.kotlin.exception.isKakaoTalkNotConnectedAccount
import team.duckie.app.android.core.datastore.PreferenceKey
import team.duckie.app.android.core.datastore.dataStore
import team.duckie.app.android.domain.kakao.usecase.GetKakaoAccessTokenUseCase
import team.duckie.app.android.feature.home.screen.MainActivity
import team.duckie.app.android.feature.onboard.constant.CollectInStep
import team.duckie.app.android.feature.onboard.constant.OnboardStep
import team.duckie.app.android.feature.onboard.screen.CategoryScreen
import team.duckie.app.android.feature.onboard.screen.LoginScreen
import team.duckie.app.android.feature.onboard.screen.ProfileScreen
import team.duckie.app.android.feature.onboard.screen.TagScreen
import team.duckie.app.android.feature.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.onboard.viewmodel.state.OnboardState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import javax.inject.Inject

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

    @Inject
    lateinit var getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase

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
            if (onboardStepState == OnboardStep.Login || onboardStepState == OnboardStep.Activity) {
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
                vm.imagePermissionGrantState.asStateFlow()
                    .collectWithLifecycle(lifecycle) { isGranted ->
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
                AnimatedContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.value),
                    targetState = onboardStepState,
                    label = "AnimatedContent",
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
            vm.finishOnboard(vm.me)
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
                    vm.finishOnboard(sideEffect.me)
                }
            }

            is OnboardSideEffect.FinishOnboard -> {
                applicationContext.dataStore.edit { preference ->
                    preference[PreferenceKey.Onboard.Finish] = true
                    sideEffect.userId?.let { preference[PreferenceKey.User.Id] = it }
                }
                changeActivityWithAnimation<MainActivity>(
                    intentBuilder = {
                        putExtra(Extras.StartGuide, sideEffect.isNewUser)
                    },
                )
            }

            is OnboardSideEffect.ReportError -> {
                sideEffect.exception.run {
                    if (isKakaoTalkNotConnectedAccount) {
                        reportToToast(message ?: "")
                    } else {
                        reportToToast()
                    }
                }
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }

            is OnboardSideEffect.NicknameDuplicateChecked -> CollectInStep
        }
    }
}
