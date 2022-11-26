/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.presentation.R
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackImage

@Composable
internal fun IntroScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .padding(systemBarPaddings)
            .padding(
                top = 78.dp,
                bottom = 34.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuackImage(
                src = team.duckie.quackquack.ui.R.drawable.quack_duckie_text_logo,
                size = DpSize(
                    width = 110.dp,
                    height = 32.dp,
                ),
            )
            // FIXME: QuackSplashSlogan 는 글자가 너무 커서 3줄이 됨
            QuackHeadLine1(text = stringResource(R.string.intro_slogan))
        }
        // TODO: SVG 필요
        QuackImage(
            modifier = Modifier.offset(x = 125.dp),
            src = R.drawable.img_duckie_intro,
            size = DpSize(
                width = 276.dp,
                height = 255.dp,
            ),
        )
    }
}
