/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackUnderlineBody3

private const val KakaoLoginScreenWelcomeLayoutId = "KakaoLoginScreenWelcome"
private const val KakaoLoginScreenLoginLayoutId = "KakaoLoginScreenLogin"

@Composable
internal fun KakaoLoginScreen() {
    Layout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .padding(systemBarPaddings)
            .padding(
                vertical = 20.dp,
                horizontal = 24.dp,
            ),
        content = {
            KakaoLoginScreenWelcome()
            KakaoLoginScreenLogin()
        },
    ) { measurables, constraints ->
        val welcomeMeasurable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == KakaoLoginScreenWelcomeLayoutId
        } ?: npe()
        val loginMeasurable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == KakaoLoginScreenLoginLayoutId
        } ?: npe()

        val looseConstraints = constraints.copy(
            minHeight = 0,
        )
        val loginPlaceable = loginMeasurable.measure(looseConstraints)
        val loginAreaHeight = loginPlaceable.height

        val maxHeight = constraints.maxHeight
        val welcomeHeight = maxHeight - loginAreaHeight

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
private fun KakaoLoginScreenWelcome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .layoutId(KakaoLoginScreenWelcomeLayoutId),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        QuackImage(
            src = R.drawable.bg_duckie_talk,
            size = DpSize(
                width = 180.dp,
                height = 248.dp,
            ),
        )
        QuackHeadLine2(
            text = stringResource(R.string.onboard_composable_kakaologin_welcome_message),
            align = TextAlign.Center,
        )
    }
}

@Composable
private fun KakaoLoginScreenLogin() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .layoutId(KakaoLoginScreenLoginLayoutId),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackImage(
            src = R.drawable.bg_login_kakao,
            size = DpSize(
                width = 320.dp,
                height = 46.dp,
            ),
            onClick = {},
        )
        // TODO: 밑줄 색상 변경 인자 오픈
        QuackUnderlineBody3(
            text = stringResource(R.string.onboard_composable_kakaologin_login_terms),
            underlineTexts = persistentListOf(
                stringResource(R.string.onboard_composable_kakaologin_hightlight_terms),
                stringResource(R.string.onboard_composable_kakaologin_hightlight_privacy),
            ),
            color = QuackColor.Gray2,
            align = TextAlign.Center,
        )
    }
}
