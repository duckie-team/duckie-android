/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.ui.profile.screen.edit

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.feature.ui.profile.component.EditTopAppBar
import team.duckie.app.android.feature.ui.profile.viewmodel.ProfileEditViewModel
import team.duckie.app.android.feature.ui.profile.viewmodel.state.ProfileScreenState
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackErrorableTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.shape.SquircleShape

private const val MaxNicknameLength = 10

@Composable
internal fun ProfileEditScreen(
    vm: ProfileEditViewModel,
) {
    val state by vm.container.stateFlow.collectAsStateWithLifecycle()
    val activity = LocalContext.current as Activity
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var photoPickerVisible by remember { mutableStateOf(false) }
    val galleryImages = remember(vm.galleryImages) { vm.galleryImages }
    val galleryImagesSelections = remember(vm.galleryImages) {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            ),
        )
    }
    var profilePhotoLastSelectionIndex by remember { mutableStateOf<Int?>(null) }
    val profilePhotoSelections = remember {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            ),
        )
    }
    var lastErrorText by remember { mutableStateOf("") }

    val takePhotoFromCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
    ) { takenPhoto ->
        photoPickerVisible = false
        if (takenPhoto != null) {
            vm.changeProfile(takenPhoto)
        }
    }

    BackHandler(photoPickerVisible) {
        photoPickerVisible = false
    }

    if (photoPickerVisible) {
        SideEffect {
            keyboardController?.hide()
            focusManager.clearFocus()
        }

        PhotoPicker(
            modifier = Modifier
                .fillMaxSize()
                .background(color = QuackColor.White.composeColor)
                .navigationBarsPadding()
                .systemBarsPadding(),
            imageUris = galleryImages,
            imageSelections = galleryImagesSelections,
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
                    vm.changeProfile(galleryImages[selectedIndex].toUri())
                }
                photoPickerVisible = false
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuackColor.White.composeColor)
            .navigationBarsPadding()
            .systemBarsPadding()
    ) {
        EditTopAppBar(
            title = stringResource(id = R.string.edit_profile),
            onBackPressed = { activity.finishWithAnimation() },
            onClickEditComplete = { activity.finishWithAnimation() },
        )
        Spacer(space = 40.dp)
        ProfileEditSection(
            profile = state.profile,
            onClickEditProfile = { photoPickerVisible = true },
        )
        QuackErrorableTextField(
            text = state.nickName,
            onTextChanged = { text ->
                if (text.length <= MaxNicknameLength) {
                    vm.changeNickName(text)
                }
            },
            placeholderText = stringResource(R.string.profile_nickname_placeholder),
            isError = state.profileState == ProfileScreenState.NicknameRuleError,
            maxLength = MaxNicknameLength,
            errorText = when (state.profileState) {
                ProfileScreenState.NicknameRuleError -> stringResource(R.string.profile_nickname_rule_error)
                ProfileScreenState.NicknameDuplicateError -> stringResource(R.string.profile_nickname_duplicate_error)
                else -> lastErrorText
            }.also { errorText ->
                lastErrorText = errorText
            },
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
            },
        )
    }
}

@Composable
internal fun ProfileEditSection(
    profile: Any?,
    onClickEditProfile: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuackImage(
            src = profile,
            shape = SquircleShape,
        )
        QuackSmallButton(
            type = QuackSmallButtonType.Border,
            text = stringResource(id = R.string.edit_profile_picture),
            enabled = true,
            onClick = onClickEditProfile,
        )
    }
}
