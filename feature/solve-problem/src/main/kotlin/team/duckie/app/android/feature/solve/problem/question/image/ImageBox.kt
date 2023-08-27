/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

@Suppress("MagicNumber")
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
    onImageLoading: (Boolean) -> Unit,
    onImageSuccess: (Boolean) -> Unit,
    isImageLoading: Boolean,
) {
    val flexibleImageHeight = getFlexibleImageHeight()
    BoxWithConstraints(modifier = modifier) {
        val actualHeight =
            if (maxHeight - spaceImageToKeyboard >= flexibleImageHeight) {
                flexibleImageHeight
            } else {
                maxHeight - spaceImageToKeyboard
            }

        ImageBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = actualHeight)
                .background(
                    color = QuackColor.Gray4.value,
                    shape = RoundedCornerShape(8.dp),
                ),
            url = url,
            onImageLoading = onImageLoading,
            onImageSuccess = onImageSuccess,
            isImageLoading = isImageLoading,
        )
    }
}

@Composable
internal fun ImageBox(
    modifier: Modifier = Modifier,
    url: String,
    onImageLoading: (Boolean) -> Unit,
    onImageSuccess: (Boolean) -> Unit,
    isImageLoading: Boolean,
) {
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize(),
            onLoading = {
                onImageLoading(true)
            },
            onSuccess = {
                onImageSuccess(false)
            },
            model = url,
            contentDescription = "",
        )
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(QuackColor.Black.value.copy(alpha = 0.5f)),
            visible = isImageLoading,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                QuackText(
                    text = stringResource(id = R.string.loading_image),
                    typography = QuackTypography.Body1.change(
                        color = QuackColor.White,
                    ),
                )
                Spacer(space = 12.dp)
                CircularProgressIndicator(color = QuackColor.DuckieOrange.value)
            }
        }
    }
}
