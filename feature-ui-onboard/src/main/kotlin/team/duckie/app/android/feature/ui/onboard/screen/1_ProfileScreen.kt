/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(FlowPreview::class)

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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
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
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.photopicker.PhotoPickerConstants
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.kotlin.runIf
import team.duckie.app.android.util.kotlin.seconds
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
private val NicknameInputDebounceSecond = 0.3.seconds

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ProfileScreen() {
    val toast = rememberToast()
    val context = LocalContext.current
    val vm = LocalViewModel.current as OnboardViewModel
    val keyboard = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    val galleryImages = remember<ImmutableList<String>>(vm.isImagePermissionGranted, vm.galleryImages) {
        if (vm.isImagePermissionGranted == true) {
            vm.galleryImages
                .toPersistentList()
                .runIf(vm.isCameraPermissionGranted) {
                    add(0, PhotoPickerConstants.Camera)
                }
        } else {
            persistentListOf()
        }
    }

    var photoPickerVisible by remember { mutableStateOf(false) }
    var profilePhoto by remember { mutableStateOf<Any>(vm.me.profileImageUrl) }

    var profilePhotoLastSelectionIndex by remember { mutableStateOf(0) }
    val profilePhotoSelections = remember {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            )
        )
    }
    val takePhotoFromCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
    ) { takenPhoto ->
        photoPickerVisible = false
        if (takenPhoto != null) {
            profilePhoto = takenPhoto
        } else {
            toast(context.getString(R.string.profile_fail_load_photo))
        }
    }

    var nickname by remember { mutableStateOf(vm.me.nickname) }
    var lastErrorText by remember { mutableStateOf("") }
    var nicknameRuleError by remember { mutableStateOf(false) }
    var debounceFinish by remember { mutableStateOf(false) }

    // TODO: 중복 닉네임 검사
    @Suppress("CanBeVal")
    var nicknameIsUseable by remember { mutableStateOf(true) }

    LaunchedEffect(vm) {
        val nicknameInputFlow = snapshotFlow { nickname }
        nicknameInputFlow
            .onEach { debounceFinish = false }
            .debounce(NicknameInputDebounceSecond)
            .collect {
                debounceFinish = true
                nicknameRuleError = vm.checkNicknameRuleError(nickname)
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
                    updateProfilePhoto = { photo ->
                        profilePhoto = photo
                    },
                    openPhotoPicker = { photoPickerVisible = true }.takeIf { vm.isImagePermissionGranted == true },
                )
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
                    isError = nicknameRuleError,
                    maxLength = MaxNicknameLength,
                    errorText = when {
                        nicknameRuleError -> stringResource(R.string.profile_nickname_rule_error)
                        !nicknameIsUseable -> stringResource(R.string.profile_nickname_duplicate_error)
                        else -> lastErrorText
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
                    enabled = debounceFinish && nickname.isNotEmpty() && !nicknameRuleError && nicknameIsUseable,
                ) {
                    navigateNextStepIfOk(
                        vm = vm,
                        debounceFinish = debounceFinish,
                        nickname = nickname,
                        nicknameRuleError = nicknameRuleError,
                        nicknameIsUseable = nicknameIsUseable,
                    )
                }
            },
            measurePolicy = ProfileScreenMeasurePolicy,
        )

        // TODO: 효율적인 애니메이션 (카메라가 로드되면서 생기는 프라임드랍 때문에 애니메이션 제거)
        if (photoPickerVisible) {
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
                    if (profilePhotoLastSelectionIndex != index) {
                        profilePhotoSelections[profilePhotoLastSelectionIndex] = false
                    }
                    profilePhotoLastSelectionIndex = index
                },
                onCloseClick = {
                    photoPickerVisible = false
                },
                onAddClick = {
                    profilePhoto = galleryImages[profilePhotoLastSelectionIndex].toUri()
                    photoPickerVisible = false
                },
            )
        }
    }
}

private fun navigateNextStepIfOk(
    vm: OnboardViewModel,
    debounceFinish: Boolean,
    nickname: String,
    nicknameRuleError: Boolean,
    nicknameIsUseable: Boolean,
) {
    if (debounceFinish && nickname.isNotEmpty() && !nicknameRuleError && nicknameIsUseable) {
        vm.navigateStep(currentStep + 1)
    }
}

private val ProfilePhotoShape = SquircleShape
private val ProfilePhotoSize = DpSize(all = 80.dp)

@Composable
private fun ProfilePhoto(
    modifier: Modifier = Modifier,
    profilePhoto: Any,
    updateProfilePhoto: (value: Any) -> Unit,
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
            src = photo,
            size = ProfilePhotoSize,
            onClick = openPhotoPicker ?: {}, // required when onLongClick is used
            onLongClick = { updateProfilePhoto(QuackIcon.Profile) },
        )
    }
}
