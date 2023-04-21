/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.question.audio

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun AudioController(
    modifier: Modifier,
    buttonText: String,
    isPlaying: () -> Boolean,
    onClick: () -> Unit,
    gifUrl: Any?,
) {
    val playing = remember(isPlaying()) { isPlaying() }
    Box(modifier = modifier) {
        QuackCrossfade(targetState = playing) {
            when (it) {
                true -> {
                    PlayingGif(
                        model = gifUrl,
                    )
                }

                else -> {
                    LargeButton(
                        text = buttonText,
                        onClick = onClick,
                    )
                }
            }
        }
    }
}

@Composable
internal fun PlayingGif(
    model: Any?,
) {
    val context = LocalContext.current
    val gifImageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
    AsyncImage(
        model = model,
        contentDescription = null,
        imageLoader = gifImageLoader,
    )
}

@Composable
private fun LargeButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier.sizeIn(
            minWidth = 82.dp,
            minHeight = 40.dp,
        ),
        backgroundColor = QuackColor.White,
        border = QuackBorder(
            color = QuackColor.Gray3,
        ),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackText(
            modifier = Modifier.padding(all = 10.dp),
            text = text,
            style = QuackTextStyle.Subtitle,
            singleLine = true,
        )
    }
}
