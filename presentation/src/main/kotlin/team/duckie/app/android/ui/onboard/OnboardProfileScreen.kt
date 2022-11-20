package team.duckie.app.android.ui.onboard

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.R
import team.duckie.app.constants.DuckieConst.MAX_USER_NAME_LENGTH
import team.duckie.app.constants.DuckieConst.MIN_USER_NAME_LENGTH
import team.duckie.app.ui.component.BackArrowTopAppBar
import team.duckie.app.ui.component.DuckieSimpleLayout
import team.duckie.app.ui.component.TitleAndDescription
import team.duckie.quackquack.ui.component.QuackCountableTextField
import team.duckie.quackquack.ui.component.QuackLargeIconRoundCard
import team.duckie.quackquack.ui.component.QuackRoundImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun OnboardProfileScreen(
    nickname: String,
    profileImage: String,
    onNickNameChange: (String) -> Unit,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    onClickProfile: () -> Unit,
) {
    val profileImageUri = requireNotNull(Uri.parse(profileImage))

    DuckieSimpleLayout(
        topAppBar = {
            BackArrowTopAppBar(
                onClick = onClickBack,
            )
        },
        content = {
            Spacer(modifier = Modifier.height(12.dp))
            TitleAndDescription(
                title = stringResource(R.string.onboard_profile_title),
                description = stringResource(R.string.onboard_profile_description),
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                QuackLargeIconRoundCard(
                    icon = QuackIcon.Camera,
                    onClick = onClickProfile,
                )
                QuackRoundImage(src = profileImageUri)
            }
            Spacer(modifier = Modifier.height(20.dp))
            QuackCountableTextField(
                text = nickname,
                onTextChanged = onNickNameChange,
                maxLength = MAX_USER_NAME_LENGTH,
                placeholderText = stringResource(R.string.onboard_username_placeholder),
                textStyle = QuackTextStyle.HeadLine2,
            )
        },
        bottomContent = {
            OnboardingButton(
                title = stringResource(R.string.next),
                onClick = onClickNext,
                active = nickname.length > MIN_USER_NAME_LENGTH,
            )
        }
    )
}

