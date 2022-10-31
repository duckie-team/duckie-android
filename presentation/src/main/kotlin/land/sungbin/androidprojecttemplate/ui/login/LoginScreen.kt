package land.sungbin.androidprojecttemplate.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import land.sungbin.androidprojecttemplate.R
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackUnderlineBody3

@Composable
fun LoginScreen(
    onClickLogin: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 70.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuackImage(
                src = R.drawable.duckie_onboarding,
                overrideSize = DpSize(200.dp, 300.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.height(20.dp))
            QuackHeadLine2(
                text = stringResource(R.string.login_introduction_message),
                align = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier
                .align(
                    alignment = Alignment.BottomCenter,
                )
                .padding(
                    bottom = 24.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuackImage(
                modifier = Modifier.padding(
                    horizontal = 20.dp
                ),
                src = R.drawable.kakao_login_large_wide,
                rippleEnabled = true,
                overrideSize = DpSize(320.dp, 48.dp),
                onClick = onClickLogin,
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuackUnderlineBody3(
                text = stringResource(R.string.terms_and_privacy_message),
                underlineText = persistentListOf(
                    stringResource(R.string.required_terms),
                    stringResource(R.string.privacy_utilization),
                ),
                undelineTextColor = QuackColor.Black,
                align = TextAlign.Center,
            )
        }
    }
}