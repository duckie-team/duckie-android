/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage

@Composable
internal fun ImageBox(
    modifier: Modifier = Modifier,
    url: String,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(
                color = QuackColor.Black.value,
                shape = RoundedCornerShape(8.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            modifier = Modifier.fillMaxSize(),
            src = url,
            contentScale = ContentScale.Fit,
        )
    }
}

@NonRestartableComposable
@Composable
private fun getFlexibleImageHeight(): Dp {
    val configuration = LocalConfiguration.current
    return ((configuration.screenWidthDp / 4) * 3).dp
}


@Composable
internal fun FlexibleImageBox(
    modifier: Modifier = Modifier,
    spaceImageToKeyboard: Dp,
    url: String,
) {
    val flexibleImageHeight = getFlexibleImageHeight()
    BoxWithConstraints(modifier = modifier) {
        val actualHeight =
            if (maxHeight - spaceImageToKeyboard >= flexibleImageHeight) {
                flexibleImageHeight
            } else {
                maxHeight - spaceImageToKeyboard
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = actualHeight)
                .background(
                    color = QuackColor.Black.value,
                    shape = RoundedCornerShape(16.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            QuackImage(
                modifier = Modifier.fillMaxSize(),
                src = url,
                contentScale = ContentScale.Fit,
            )
        }
    }
}
