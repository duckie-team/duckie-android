/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.audio

import android.os.Build.VERSION.SDK_INT
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
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
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
        QuackAnimatedVisibility(visible = playing.not()) {
            LargeButton(
                text = buttonText,
                onClick = onClick,
            )
        }
        if (playing) {
            PlayingGif(
                model = gifUrl,
            )
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
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
    AsyncImage(
        model = "https://s3-alpha-sig.figma.com/img/b797/6283/a4e5becc8777544c6f9489d48ac593c8?Expires=1675641600&Signature=mLfLrS5~d7rNyYp7bUfNmxbXteqczEvrXMjPVKFUHqh6ghNfVG0ojEgg8J1U7IDxRv1DQXt0ixhDqd~yqb~EmfdA8kIIcZaCiM1UhB1THYdppHcXNqCCAA6ZaVY-OnTe4AAXSlw78IMBUtz4sR9Xv8Fid8ZYnvaenUy8fQSN-LMLkcM8dzSzshThp-tvAwcZE-38PAy4iT2a4ebn4c5UMgELIdReMJTDSyKsnvywMx-bugaQGx0hD4Tm1kPMDBgFdfVe~BooZ5s99xokwzCFDINFYMR~iJcJswdGsONHm4Oz7YfeUx-CkigVlIxzlyFqc5w9xlgnQoYq5LP17Gl6hA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
        contentDescription = null,
        imageLoader = gifImageLoader
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
            color = QuackColor.Gray3
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
