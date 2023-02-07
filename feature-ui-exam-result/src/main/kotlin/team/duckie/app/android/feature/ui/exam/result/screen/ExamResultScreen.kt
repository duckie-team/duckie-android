/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.exam.result.ExamResultActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun ExamResultScreen() {
    val activity = LocalContext.current as ExamResultActivity

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            QuackTopAppBar(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 16.dp),
                leadingIcon = QuackIcon.ArrowBack,
                onLeadingIconClick = {
                    activity.finishWithAnimation()
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                GrayBorderSmallButton(
                    modifier = Modifier
                        .heightIn(min = 44.dp)
                        .weight(1f),
                    text = "다시 풀기",
                    onClick = {

                    },
                )
                QuackSmallButton(
                    modifier = Modifier
                        .heightIn(44.dp)
                        .weight(1f),
                    type = QuackSmallButtonType.Fill,
                    text = "시험 끝내기",
                    enabled = true,
                    onClick = {

                    },
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(all = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            QuackImage(
                modifier = Modifier.fillMaxSize(),
                src = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1673888692440",
            )
        }
    }
}

@Composable
private fun GrayBorderSmallButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier,
        backgroundColor = QuackColor.White,
        border = QuackBorder(color = QuackColor.Gray3),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackSubtitle(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 12.dp,
            ),
            text = text,
            singleLine = true,
        )
    }
}
