/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.profile.screen.edit

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.ui.PhotoPicker
import team.duckie.app.android.feature.profile.R
import team.duckie.app.android.feature.profile.component.EditTopAppBar
import team.duckie.app.android.feature.profile.component.GrayBorderButton
import team.duckie.app.android.feature.profile.viewmodel.ProfileEditViewModel
import team.duckie.app.android.feature.profile.viewmodel.state.NicknameState
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackErrorableTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.util.DpSize

private const val MaxNicknameLength = 12
private const val MaxIntroductionLength = 60

@Composable
internal fun ProfileEditScreen(
    vm: ProfileEditViewModel,
) {
    val state by vm.container.stateFlow.collectAsStateWithLifecycle()
    val galleryState = remember(state) { state.galleryState }
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val takePhotoFromCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
    ) { takenPhoto ->
        vm.changePhotoPickerVisible(false)
        if (takenPhoto != null) {
            vm.changeProfile(takenPhoto)
        }
    }

    BackHandler(galleryState.visible) {
        vm.changePhotoPickerVisible(false)
    }

    if (galleryState.visible) {
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
            imageUris = galleryState.images,
            imageSelections = galleryState.imagesSelections,
            onCameraClick = {
                coroutineScope.launch { takePhotoFromCameraLauncher.launch() }
            },
            onImageClick = { index, _ ->
                vm.clickGalleryImage(index)
            },
            onCloseClick = {
                vm.changePhotoPickerVisible(false)
            },
            onAddClick = {
                vm.addProfileFromGallery()
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuackColor.White.composeColor)
            .navigationBarsPadding()
            .systemBarsPadding(),
    ) {
        EditTopAppBar(
            title = stringResource(id = R.string.edit_profile),
            onBackPressed = vm::clickBackPress,
            onClickEditComplete = vm::clickEditComplete,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(space = 40.dp)
            ProfileEditSection(
                profile = state.profile,
                onClickEditProfile = vm::clickEditProfile,
            )
            Spacer(space = 40.dp)
            QuackBody1(
                text = stringResource(R.string.nickname),
                color = QuackColor.Gray1,
            )
            QuackErrorableTextField(
                modifier = Modifier.skeleton(state.isLoading),
                text = state.nickname,
                onTextChanged = { text ->
                    if (text.length <= MaxNicknameLength) {
                        vm.changeNickName(text)
                    }
                },
                placeholderText = stringResource(R.string.profile_nickname_placeholder),
                isError = state.nicknameState.isInValid(),
                maxLength = MaxNicknameLength,
                errorText = when (state.nicknameState) {
                    NicknameState.NicknameRuleError -> stringResource(R.string.profile_nickname_rule_error)
                    NicknameState.NicknameDuplicateError -> stringResource(R.string.profile_nickname_duplicate_error)
                    else -> ""
                },
                keyboardActions = KeyboardActions {
                    keyboardController?.hide()
                },
            )
            Spacer(space = 36.dp)
            QuackBody1(
                text = stringResource(R.string.introduce),
                color = QuackColor.Gray1,
            )
            Spacer(space = 8.dp)
            QuackReviewTextArea(
                // TODO(evergreenTree97) 배포 후 글자 수 제한있는 텍스트필드 구현
                modifier = Modifier.skeleton(state.isLoading),
                text = state.introduce,
                onTextChanged = {
                    if (it.length <= MaxIntroductionLength) {
                        vm.inputIntroduce(it)
                    }
                },
                focused = state.introduceFocused,
                placeholderText = stringResource(id = R.string.please_input_introduce),
            )
        }
    }
}

@Composable
internal fun ProfileEditSection(
    profile: Any?,
    onClickEditProfile: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QuackImage(
            src = profile,
            shape = SquircleShape,
            size = DpSize(80.dp),
        )
        GrayBorderButton(
            text = stringResource(id = R.string.edit_profile_picture),
            onClick = onClickEditProfile,
        )
    }
}
