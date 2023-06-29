/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

@Composable
fun LeftChatBubble(
    message: String,
    backgroundColor: QuackColor,
    textColor: QuackColor = QuackColor.Black,
) {
    Row(
        verticalAlignment = Alignment.Top,
    ) {
        Image(
            modifier = Modifier.offset(x = 6.dp),
            painter = painterResource(id = R.drawable.ic_chat_tail),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = backgroundColor.value)
        )
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor.value,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 6.dp,
                ),
        ) {
            QuackText(
                text = message,
                typography = QuackTypography.Body2.change(
                    color = textColor
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewChat() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 16.dp)
    ) {
        LeftChatBubble(message = "테스트세트스", backgroundColor = QuackColor(Color(0xFFFFEFCF)))
    }
}
