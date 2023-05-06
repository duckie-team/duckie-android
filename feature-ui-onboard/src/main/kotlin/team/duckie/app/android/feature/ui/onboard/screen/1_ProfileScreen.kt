/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(FlowPreview::class, ExperimentalComposeUiApi::class)
@file:Suppress("ConstPropertyName", "PrivatePropertyName")

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.photopicker.PhotoPickerConstants
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.ProfileScreenState
import team.duckie.app.android.shared.ui.compose.constant.SharedIcon
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.kotlin.runIf
import team.duckie.app.android.util.ui.const.Debounce
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackErrorableTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.util.DpSize

private val currentStep = OnboardStep.Profile

private const val ProfileScreenTopAppBarLayoutId = "ProfileScreenTopAppBar"
private const val ProfileScreenTitleAndDescriptionLayoutId = "ProfileScreenTitleAndDescription"
private const val ProfileScreenProfileImageLayoutId = "ProfileScreenProfileImage"
private const val ProfileScreenNicknameTextFieldLayoutId = "ProfileScreenNicknameTextField"
private const val ProfileScreenNextButtonLayoutId = "ProfileScreenNextButton"

private val ProfileScreenMeasurePolicy = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(width = true)

    val topAppBarPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == ProfileScreenTopAppBarLayoutId
    }?.measure(looseConstraints) ?: npe()

    val titileAndDescriptionPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == ProfileScreenTitleAndDescriptionLayoutId
    }?.measure(looseConstraints) ?: npe()

    val profileImagePlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == ProfileScreenProfileImageLayoutId
    }?.measure(extraLooseConstraints) ?: npe()

    val nicknameTextFieldPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == ProfileScreenNicknameTextFieldLayoutId
    }?.measure(looseConstraints) ?: npe()

    val nextButtonPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == ProfileScreenNextButtonLayoutId
    }?.measure(looseConstraints) ?: npe()

    val topAppBarHeight = topAppBarPlaceable.height
    val titleAndDescriptionHeight = titileAndDescriptionPlaceable.height
    val profileImageHeight = profileImagePlaceable.height
    val nextButtonHeight = nextButtonPlaceable.height

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarPlaceable.place(
            x = 0,
            y = 0,
        )
        titileAndDescriptionPlaceable.place(
            x = 0,
            y = topAppBarHeight,
        )
        profileImagePlaceable.place(
            x = Alignment.CenterHorizontally.align(
                size = profileImagePlaceable.width,
                space = constraints.maxWidth,
                layoutDirection = layoutDirection,
            ),
            y = topAppBarHeight + titleAndDescriptionHeight,
        )
        nicknameTextFieldPlaceable.place(
            x = 0,
            y = topAppBarHeight + titleAndDescriptionHeight + profileImageHeight,
        )
        nextButtonPlaceable.place(
            x = 0,
            y = constraints.maxHeight - nextButtonHeight,
        )
    }
}

private const val MaxNicknameLength = 10

@Composable
internal fun ProfileScreen(vm: OnboardViewModel = activityViewModel()) {
    val toast = rememberToast()
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    @Suppress("RemoveExplicitTypeArguments")
    val galleryImages =
        remember<ImmutableList<String>>(vm.isImagePermissionGranted, vm.galleryImages) {
            if (vm.isImagePermissionGranted == true) {
                vm.galleryImages.runIf(vm.isCameraPermissionGranted) {
                    toPersistentList().add(0, PhotoPickerConstants.Camera)
                }
            } else {
                persistentListOf()
            }
        }

    var photoPickerVisible by remember { mutableStateOf(false) }
    var profilePhoto by remember { mutableStateOf<Any>(vm.profileImageUrl ?: "") }

    var profilePhotoLastSelectionIndex by remember { mutableStateOf<Int?>(null) }
    val profilePhotoSelections = remember {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            ),
        )
    }
    val takePhotoFromCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
    ) { takenPhoto ->
        photoPickerVisible = false
        if (takenPhoto != null) {
            profilePhoto = takenPhoto.also(vm::updateUserProfileImageFile)
        } else {
            // TODO(sungbin): 유저가 취소했을 때도 표시해야 할까?
            toast(context.getString(R.string.profile_fail_capture_photo))
        }
    }

    var nickname by remember {
        mutableStateOf(
            if (vm.container.stateFlow.value.temporaryNickname.isNullOrEmpty()) {
                vm.me.nickname
            } else {
                vm.container.stateFlow.value.temporaryNickname!!
            },
        )
    }
    var lastErrorText by remember { mutableStateOf("") }

    LaunchedEffect(vm) {
        val nicknameInputFlow = snapshotFlow { nickname }
        nicknameInputFlow
            .onEach { vm.readyToScreenCheck(currentStep) }
            .debounce(Debounce.SearchSecond)
            .collect { nickname ->
                vm.checkNickname(nickname)
            }
    }

    BackHandler(photoPickerVisible) {
        photoPickerVisible = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Layout(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .padding(bottom = systemBarPaddings.calculateBottomPadding() + 16.dp),
            content = {
                val profileScreenState = vm.collectAsState().value.profileState
                OnboardTopAppBar(
                    modifier = Modifier.layoutId(ProfileScreenTopAppBarLayoutId),
                    currentStep = currentStep,
                )
                TitleAndDescription(
                    modifier = Modifier
                        .layoutId(ProfileScreenTitleAndDescriptionLayoutId)
                        .padding(
                            top = 12.dp,
                            start = 20.dp,
                            end = 20.dp,
                        ),
                    titleRes = R.string.profile_title,
                    descriptionRes = R.string.profile_description,
                )
                ProfilePhoto(
                    modifier = Modifier
                        .layoutId(ProfileScreenProfileImageLayoutId)
                        .padding(
                            // 항상 center 에 배치돼서 horizontal padding 불필요
                            top = 32.dp,
                            bottom = 20.dp,
                        ),
                    profilePhoto = profilePhoto,
                    resetProfilePhoto = {
                        profilePhoto = QuackIcon.Profile
                        profilePhotoLastSelectionIndex?.let { lastSelectionIndex ->
                            profilePhotoSelections[lastSelectionIndex] = false
                        }
                    },
                    openPhotoPicker = {
                        photoPickerVisible = true
                    }.takeIf { vm.isImagePermissionGranted == true },
                )
                // TODO(sungbin): https://github.com/duckie-team/quack-quack-android/issues/438
                QuackErrorableTextField(
                    modifier = Modifier
                        .layoutId(ProfileScreenNicknameTextFieldLayoutId)
                        // 패딩이 왜 2배로 들어가지??
                        .padding(horizontal = 10.dp),
                    text = nickname,
                    onTextChanged = { text ->
                        if (text.length <= MaxNicknameLength) {
                            nickname = text
                        }
                    },
                    placeholderText = stringResource(R.string.profile_nickname_placeholder),
                    isError = ProfileScreenState.errorState.contains(profileScreenState),
                    maxLength = MaxNicknameLength,
                    errorText = when (profileScreenState) {
                        ProfileScreenState.NicknameRuleError -> stringResource(R.string.profile_nickname_rule_error)
                        ProfileScreenState.NicknameDuplicateError -> stringResource(R.string.profile_nickname_duplicate_error)
                        ProfileScreenState.NicknameEmpty -> stringResource(R.string.profile_nickname_empty)
                        else -> lastErrorText // 안하면 invisible 될 때 갑자기 텍스트가 사라짐 (애니메이션 X)
                    }.also { errorText ->
                        lastErrorText = errorText
                    },
                    keyboardActions = KeyboardActions {
                        keyboard?.hide()
                    },
                )
                QuackLargeButton(
                    modifier = Modifier
                        .layoutId(ProfileScreenNextButtonLayoutId)
                        .padding(horizontal = 20.dp),
                    text = stringResource(R.string.button_next),
                    type = QuackLargeButtonType.Fill,
                    imeAnimation = true,
                    enabled = profileScreenState == ProfileScreenState.Valid && nickname.isNotEmpty(),
                ) {
                    navigateNextStep(
                        vm = vm,
                        nickname = nickname,
                    )
                }
            },
            measurePolicy = ProfileScreenMeasurePolicy,
        )

        // TODO(sungbin): 효율적인 애니메이션 (카메라가 로드되면서 생기는 프라임드랍 때문에 애니메이션 제거)
        if (photoPickerVisible) {
            SideEffect {
                keyboard?.hide()
                focusManager.clearFocus()
            }

            PhotoPicker(
                modifier = Modifier
                    .padding(top = systemBarPaddings.calculateTopPadding())
                    .fillMaxSize()
                    .background(color = QuackColor.White.composeColor),
                imageUris = galleryImages,
                imageSelections = profilePhotoSelections,
                onCameraClick = {
                    coroutineScope.launch {
                        takePhotoFromCameraLauncher.launch()
                    }
                },
                onImageClick = { index, _ ->
                    profilePhotoSelections[index] = !profilePhotoSelections[index]
                    profilePhotoLastSelectionIndex?.let { lastSelectionIndex ->
                        if (lastSelectionIndex != index) {
                            profilePhotoSelections[lastSelectionIndex] = false
                        }
                    }
                    profilePhotoLastSelectionIndex = index
                },
                onCloseClick = {
                    photoPickerVisible = false
                },
                onAddClick = {
                    profilePhotoLastSelectionIndex?.let { selectedIndex ->
                        profilePhoto = galleryImages[selectedIndex].toUri()
                            .also(vm::updateUserProfileImageFile)
                    }
                    photoPickerVisible = false
                },
            )
        }
    }
}

private val ProfilePhotoShape = SquircleShape
private val ProfilePhotoSize = DpSize(all = 80.dp)

@Composable
private fun ProfilePhoto(
    modifier: Modifier = Modifier,
    profilePhoto: Any,
    resetProfilePhoto: () -> Unit,
    openPhotoPicker: (() -> Unit)?,
) {
    QuackAnimatedContent(
        modifier = modifier
            .size(ProfilePhotoSize)
            .clip(ProfilePhotoShape)
            .quackClickable(onClick = openPhotoPicker),
        targetState = profilePhoto,
    ) { photo ->
        QuackImage(
            src = if (photo == "") SharedIcon.ic_default_profile else photo,
            size = ProfilePhotoSize,
            contentScale = ContentScale.Crop,
            onClick = openPhotoPicker ?: {}, // required when onLongClick is used
            onLongClick = resetProfilePhoto,
        )
    }
}

private fun navigateNextStep(
    vm: OnboardViewModel,
    nickname: String,
) {
    vm.updateUserNickname(nickname)
    vm.navigateStep(currentStep + 1)
}
