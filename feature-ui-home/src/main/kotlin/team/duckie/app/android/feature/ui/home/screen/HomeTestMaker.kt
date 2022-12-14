/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.util.DpSize

private val HomeProfileSize: DpSize = DpSize(
    all = 24.dp,
)

// TODO("임의의 값, figma에 명시 X")
private val HomeProfileShape: RoundedCornerShape = RoundedCornerShape(
    size = 16.dp
)

@Composable
fun HomeTestMaker(
    profile: String,
    title: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickUserProfile: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackImage(
            src = profile,
            size = HomeProfileSize,
            shape = HomeProfileShape,
            onClick = {
                if (onClickUserProfile != null) {
                    onClickUserProfile()
                }
            },
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            QuackSubtitle2(
                text = title,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                QuackBody3(text = name)

                Spacer(modifier = Modifier.width(8.dp))

                QuackBody3(
                    text = "응시자 $takers",
                    color = QuackColor.Gray2,
                )

                Spacer(modifier = Modifier.width(4.dp))

                QuackBody3(
                    text = "·",
                    color = QuackColor.Gray2,
                )

                Spacer(modifier = Modifier.width(4.dp))

                QuackBody3(
                    text = createAt,
                    color = QuackColor.Gray2,
                )
            }
        }
    }
}
