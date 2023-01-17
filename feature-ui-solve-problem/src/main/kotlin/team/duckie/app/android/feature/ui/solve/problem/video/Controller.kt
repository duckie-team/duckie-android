/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.solve.problem.R
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
internal fun VideoController(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    progress: () -> Float,
    onToggle: () -> Unit,
    onProgressChanged: (Float) -> Unit,
) {
    QuackAnimatedVisibility(
        modifier = modifier,
        visible = isVisible()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            InteractionButton(
                modifier = Modifier.align(alignment = Alignment.Center),
                onClick = onToggle,
                isPlaying = isPlaying,
            )
            BottomSlider(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                value = progress,
                onValueChanged = onProgressChanged,
            )
        }
    }
}


@Composable
internal fun InteractionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isPlaying: () -> Boolean,
) {
    Box(
        modifier = modifier
            .size(size = 40.dp)
            .clip(shape = CircleShape)
            .quackClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .alpha(alpha = 0.2f)
                .background(color = QuackColor.Black.composeColor),
        )
        QuackImage(
            modifier = modifier,
            src = when (isPlaying()) {
                false -> R.drawable.video_pause
                true -> R.drawable.video_play
            },
            contentScale = ContentScale.None
        )
    }
}

@Composable
internal fun BottomSlider(
    modifier: Modifier = Modifier,
    value: () -> Float,
    onValueChanged: (Float) -> Unit,
) {
    val mainColor = QuackColor.DuckieOrange.composeColor
    val inActiveColor = QuackColor.Gray3.composeColor
    Slider(
        modifier = modifier,
        value = value(),
        onValueChange = onValueChanged,
        colors = SliderDefaults.colors(
            thumbColor = mainColor,
            activeTrackColor = mainColor,
            inactiveTrackColor = inActiveColor,
        )
    )
}
