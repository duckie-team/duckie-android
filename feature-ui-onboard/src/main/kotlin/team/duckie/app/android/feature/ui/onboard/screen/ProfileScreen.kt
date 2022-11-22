/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackProfileTextField
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

private const val MaxNicknameLength = 10

// TODO: 정확한 radius 사이즈 필요 (제플린에서 안보임)
private val ProfilePhotoShape = RoundedCornerShape(size = 30.dp)

private val ProfilePhotoSize = DpSize(all = 80.dp)

@Composable
internal fun ProfileScreen() {
    val vm = LocalViewModel.current as OnboardViewModel
    var nickname by remember { mutableStateOf("") }
    val nicknameRuleError by remember {
        derivedStateOf {
            vm.checkNicknameRuleError(nickname)
        }
    }
    // TODO: 중복 닉네임 검사
    var nicknameIsUseable by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 16.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfilePhoto()
            TitleAndDescription(
                titleRes = R.string.profile_title,
                descriptionRes = R.string.profile_description,
            )
            // TODO: onCleared 인자 선택적으로 변경
            // TODO: 닉네임 중복 검사 결과에 따른 에러 메시지 결정
            // TODO: 네이밍을 QuackErrorableTextField 으로 변경 후, error state 를 인자로 받게 변경
            // TODO: 애니메이션 제거 (퍼포먼스를 너무 저하시킴)
            QuackProfileTextField(
                text = nickname,
                onTextChanged = { nickname = it },
                placeholderText = stringResource(R.string.profile_nickname_placeholder),
                maxLength = MaxNicknameLength,
                errorText = stringResource(R.string.profile_nickname_error),
                onCleared = { nickname = "" },
                keyboardActions = KeyboardActions {
                    navigationNextStepIfOk(
                        nickname = nickname,
                        nicknameRuleError = nicknameRuleError,
                        nicknameIsUseable = nicknameIsUseable,
                        vm = vm,
                    )
                },
            )
        }
        QuackLargeButton(
            text = stringResource(R.string.button_next),
            type = QuackLargeButtonType.Fill,
            enabled = nickname.isNotEmpty() && !nicknameRuleError && nicknameIsUseable,
        ) {
            navigationNextStepIfOk(
                nickname = nickname,
                nicknameRuleError = nicknameRuleError,
                nicknameIsUseable = nicknameIsUseable,
                vm = vm,
            )
        }
    }
}

private fun navigationNextStepIfOk(
    nickname: String,
    nicknameRuleError: Boolean,
    nicknameIsUseable: Boolean,
    vm: OnboardViewModel,
) {
    if (nickname.isNotEmpty() && !nicknameRuleError && nicknameIsUseable) {
        vm.updateStep(vm.currentStep + 1)
    }
}

@Composable
private fun ProfilePhoto() {
    var profilePhoto by remember { mutableStateOf<Any?>(null) }

    // TODO: 길게 눌러서 설정한 프로필 사진 제거
    QuackAnimatedContent(
        modifier = Modifier
            .padding(
                top = 32.dp,
                bottom = 20.dp,
            )
            .size(ProfilePhotoSize)
            .clip(ProfilePhotoShape)
            .clickable {
                // TODO: photo picker
                profilePhoto = QuackIcon.Profile
            },
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
                )
            }
        }
    }
}
