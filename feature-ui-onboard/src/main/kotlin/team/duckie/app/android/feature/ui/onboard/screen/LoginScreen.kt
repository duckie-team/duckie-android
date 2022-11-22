/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackUnderlineBody3

private const val LoginScreenWelcomeLayoutId = "LoginScreenWelcome"
private const val LoginScreenLoginAreaLayoutId = "LoginScreenLoginArea"

@Composable
internal fun LoginScreen() {
    Layout(
        modifier = Modifier
            .fillMaxSize()
            .padding(systemBarPaddings)
            .padding(
                vertical = 20.dp,
                horizontal = 24.dp,
            ),
        content = {
            LoginScreenWelcome()
            LoginScreenLoginArea()
        },
    ) { measurables, constraints ->
        val looseConstraints = constraints.asLoose()

        val welcomeMeasurable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == LoginScreenWelcomeLayoutId
        } ?: npe()

        val loginPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == LoginScreenLoginAreaLayoutId
        }?.measure(looseConstraints) ?: npe()

        val loginAreaHeight = loginPlaceable.height
        val welcomeHeight = constraints.maxHeight - loginAreaHeight

        val welcomeConstraints = constraints.copy(
            minHeight = welcomeHeight,
            maxHeight = welcomeHeight,
        )
        val welcomePlaceable = welcomeMeasurable.measure(welcomeConstraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            welcomePlaceable.place(
                x = 0,
                y = 0,
            )
            loginPlaceable.place(
                x = 0,
                y = welcomeHeight,
            )
        }
    }
}

@Composable
private fun LoginScreenWelcome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .layoutId(LoginScreenWelcomeLayoutId),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        QuackImage(
            src = R.drawable.img_duckie_talk,
            size = DpSize(
                width = 180.dp,
                height = 248.dp,
            ),
        )
        QuackHeadLine2(
            text = stringResource(R.string.kakaologin_welcome_message),
            align = TextAlign.Center,
        )
    }
}

@Composable
private fun LoginScreenLoginArea() {
    val vm = LocalViewModel.current as OnboardViewModel

    Column(
        modifier = Modifier.layoutId(LoginScreenLoginAreaLayoutId),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackImage(
            src = R.drawable.img_login_kakao,
            size = DpSize(
                width = 320.dp,
                height = 46.dp,
            ),
            onClick = {
                // TODO: 카카오 로그인
                vm.updateStep(vm.currentStep + 1)
            },
        )
        // TODO: 밑줄 색상 변경 인자 오픈
        QuackUnderlineBody3(
            text = stringResource(R.string.kakaologin_login_terms),
            underlineTexts = persistentListOf(
                stringResource(R.string.kakaologin_hightlight_terms),
                stringResource(R.string.kakaologin_hightlight_privacy),
            ),
            color = QuackColor.Gray2,
            align = TextAlign.Center,
        )
    }
}
