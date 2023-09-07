/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.solve.problem.question.audio

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

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
    QuackButton(
        modifier = modifier,
        text = text,
        style = QuackButtonStyle.SecondaryRoundSmall,
        onClick = onClick,
    )
}
