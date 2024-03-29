/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalDesignToken::class,
    ExperimentalQuackQuackApi::class,
)

package team.duckie.app.android.feature.profile.screen.edit

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.ui.PhotoPicker
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.quack.todo.QuackErrorableTextField
import team.duckie.app.android.common.compose.ui.quack.todo.QuackErrorableTextFieldState
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.common.compose.util.addFocusCleaner
import team.duckie.app.android.feature.profile.R
import team.duckie.app.android.feature.profile.component.EditTopAppBar
import team.duckie.app.android.feature.profile.component.GrayBorderButton
import team.duckie.app.android.feature.profile.viewmodel.ProfileEditViewModel
import team.duckie.app.android.feature.profile.viewmodel.state.NicknameState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.shape.SquircleShape
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextArea
import team.duckie.quackquack.ui.QuackTextAreaStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.textAreaCounter
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private const val MaxNicknameLength = 10
private const val MaxIntroductionLength = 60

@Composable
internal fun ProfileEditScreen(
    vm: ProfileEditViewModel,
) {
    val context = LocalContext.current.applicationContext
    val state by vm.container.stateFlow.collectAsStateWithLifecycle()

    // keyboard focused
    val interactionSource = remember { MutableInteractionSource() }
    val introduceTextFieldFocused = interactionSource.collectIsFocusedAsState().value

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
                .background(color = QuackColor.White.value)
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
            .background(QuackColor.White.value)
            .addFocusCleaner()
            .navigationBarsPadding()
            .systemBarsPadding(),
    ) {
        EditTopAppBar(
            title = stringResource(id = R.string.edit_profile),
            onBackPressed = vm::clickBackPress,
            onClickEditComplete = {
                vm.clickEditComplete(applicationContext = context.applicationContext)
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(space = 40.dp)
            ProfileEditSection(
                profile = state.profile,
                onClickEditProfile = vm::loadGalleryImages,
            )
            Spacer(space = 40.dp)
            QuackText(
                text = stringResource(R.string.nickname),
                typography = QuackTypography.Body1.change(color = QuackColor.Gray1),
            )
            Spacer(space = 16.dp)
            QuackErrorableTextField(
                modifier = Modifier.skeleton(state.isLoading),
                text = state.nickname,
                onTextChanged = { text ->
                    if (text.length <= MaxNicknameLength) {
                        vm.changeNickName(text)
                    }
                },
                placeholderText = stringResource(R.string.profile_nickname_placeholder),
                maxLength = MaxNicknameLength,
                textFieldState = when (state.nicknameState) {
                    NicknameState.NicknameRuleError -> QuackErrorableTextFieldState.Error(
                        errorText = stringResource(R.string.profile_nickname_rule_error),
                    )

                    NicknameState.NicknameDuplicateError -> QuackErrorableTextFieldState.Error(
                        errorText = stringResource(R.string.profile_nickname_duplicate_error),
                    )

                    NicknameState.Valid -> QuackErrorableTextFieldState.Success(
                        successText = stringResource(R.string.profile_nickname_valid),
                    )

                    else -> QuackErrorableTextFieldState.Normal
                },
                keyboardActions = KeyboardActions {
                    keyboardController?.hide()
                },
            )
            Spacer(space = 36.dp)
            QuackText(
                text = stringResource(R.string.introduce),
                typography = QuackTypography.Body1.change(color = QuackColor.Gray1),
            )
            Spacer(space = 8.dp)
            QuackTextArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (introduceTextFieldFocused) {
                            QuackColor.DuckieOrange.value
                        } else {
                            QuackColor.Gray3.value
                        },
                        shape = RoundedCornerShape(8.dp),
                    )
                    .textAreaCounter(maxLength = 60),
                style = QuackTextAreaStyle.Default,
                value = state.introduce,
                onValueChange = {
                    if (it.length <= MaxIntroductionLength) {
                        vm.inputIntroduce(it)
                    }
                },
                placeholderText = stringResource(id = R.string.please_input_introduce),
                interactionSource = interactionSource,
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
        if (profile?.toString().isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .background(QuackColor.Gray3.value, SquircleShape),
            )
        } else {
            QuackImage(
                src = "$profile",
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(SquircleShape),
                contentScale = ContentScale.Crop,
            )
        }
        GrayBorderButton(
            text = stringResource(id = R.string.edit_profile_picture),
            onClick = onClickEditProfile,
        )
    }
}
