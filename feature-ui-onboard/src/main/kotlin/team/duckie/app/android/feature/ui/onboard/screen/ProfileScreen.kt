/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(FlowPreview::class)

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.constaint.OnboardStep
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackErrorableTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

private const val ProfileScreenTitleAndDescriptionLayoutId = "ProfileScreenTitleAndDescription"
private const val ProfileScreenProfileImageLayoutId = "ProfileScreenProfileImage"
private const val ProfileScreenNicknameTextFieldLayoutId = "ProfileScreenNicknameTextField"
private const val ProfileScreenNextButtonLayoutId = "ProfileScreenNextButton"

private const val MaxNicknameLength = 10

// FIXME: 정확한 radius 사이즈 필요 (제플린에서 안보임)
private val ProfilePhotoShape = RoundedCornerShape(size = 30.dp)
private val ProfilePhotoSize = DpSize(all = 80.dp)

private const val NicknameInputDebounceSecond = 0.3

private val currentStep = OnboardStep.Profile
internal val galleryImages = mutableStateOf<ImmutableList<String>>(persistentListOf())

@Composable
internal fun ProfileScreen() {
    val vm = LocalViewModel.current as OnboardViewModel
    var nickname by remember { mutableStateOf("") }
    var lastErrorText by remember { mutableStateOf("") }
    var nicknameRuleError by remember { mutableStateOf(false) }
    var debounceFinish by remember { mutableStateOf(false) }

    @Suppress("CanBeVal") // TODO: 중복 닉네임 검사
    var nicknameIsUseable by remember { mutableStateOf(true) }

    LaunchedEffect(vm) {
        val nicknameInputFlow = snapshotFlow { nickname }
        nicknameInputFlow
            .onEach { debounceFinish = false }
            .debounce(NicknameInputDebounceSecond.seconds)
            .collect {
                debounceFinish = true
                nicknameRuleError = vm.checkNicknameRuleError(nickname)
            }
    }

    Layout(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = systemBarPaddings.calculateBottomPadding())
            .padding(
                top = 12.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 16.dp,
            ),
        content = {
            TitleAndDescription(
                modifier = Modifier.layoutId(ProfileScreenTitleAndDescriptionLayoutId),
                titleRes = R.string.profile_title,
                descriptionRes = R.string.profile_description,
            )
            ProfilePhoto()
            QuackErrorableTextField(
                modifier = Modifier.layoutId(ProfileScreenNicknameTextFieldLayoutId),
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
                    navigateNextStepIfOk(
                        vm = vm,
                        debounceFinish = debounceFinish,
                        nickname = nickname,
                        nicknameRuleError = nicknameRuleError,
                        nicknameIsUseable = nicknameIsUseable,
                    )
                },
            )
            QuackLargeButton(
                modifier = Modifier.layoutId(ProfileScreenNextButtonLayoutId),
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
    ) { measurables, constraints ->
        val looseConstraints = constraints.asLoose()
        val extraLooseConstraints = constraints.asLoose(width = true)

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

        val titleAndDescriptionHeight = titileAndDescriptionPlaceable.height
        val profileImageHeight = profileImagePlaceable.height
        val nextButtonHeight = nextButtonPlaceable.height

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            titileAndDescriptionPlaceable.place(
                x = 0,
                y = 0,
            )
            profileImagePlaceable.place(
                x = Alignment.CenterHorizontally.align(
                    size = profileImagePlaceable.width,
                    space = constraints.maxWidth,
                    layoutDirection = layoutDirection,
                ),
                y = titleAndDescriptionHeight,
            )
            nicknameTextFieldPlaceable.place(
                x = 0,
                y = titleAndDescriptionHeight + profileImageHeight,
            )
            nextButtonPlaceable.place(
                x = 0,
                y = constraints.maxHeight - nextButtonHeight,
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
        vm.updateStep(currentStep + 1)
    }
}

@Composable
private fun ProfilePhoto() {
    var profilePhoto by remember { mutableStateOf<Any?>(null) }

    val openPhotoPicker: () -> Unit = remember {
        {
            // TODO: photo picker
            profilePhoto = QuackIcon.Profile
        }
    }

    QuackAnimatedContent(
        modifier = Modifier
            .layoutId(ProfileScreenProfileImageLayoutId)
            .padding(
                top = 32.dp,
                bottom = 20.dp,
            )
            .size(ProfilePhotoSize)
            .clip(ProfilePhotoShape)
            .clickable(onClick = openPhotoPicker),
        targetState = profilePhoto,
    ) { photo ->
        when (photo) {
            null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.Gray3.composeColor),
                    contentAlignment = Alignment.Center,
                ) {
                    QuackImage(
                        src = QuackIcon.Camera,
                        size = DpSize(all = 22.dp),
                        tint = QuackColor.Gray2,
                    )
                }
            }
            else -> {
                QuackImage(
                    src = photo,
                    size = ProfilePhotoSize,
                    onClick = openPhotoPicker, // required when onLongClick is used
                    onLongClick = {
                        profilePhoto = null
                    },
                )
            }
        }
    }
}

@Composable
private fun PhotoPicker() {

}
