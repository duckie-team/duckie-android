/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.solveproblem.question.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.solve.problem.R
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.modifier.quackClickable
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
internal fun VideoController(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    totalTime: () -> Long,
    currentTime: () -> Long,
    buffedPercentage: () -> Int,
    onToggle: () -> Unit,
    onTimeChanged: (Float) -> Unit,
) {
    var sliderHeight by remember { mutableStateOf(0) }
    QuackAnimatedVisibility(
        modifier = modifier,
        visible = isVisible(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            InteractionButton(
                modifier = Modifier.align(alignment = Alignment.Center),
                onClick = onToggle,
                isPlaying = isPlaying,
            )
            Column(
                modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .offset {
                    IntOffset(
                        x = 0,
                        y = sliderHeight / 2,
                    )
                },
            ) {
                QuackBody3(
                    modifier = Modifier.padding(start = 12.dp),
                    text = stringResource(
                        id = R.string.current_between_total,
                        currentTime().formatMinSec(),
                        totalTime().formatMinSec(),
                    ),
                    color = QuackColor.Gray3,
                )
                VideoSlider(
                    modifier = Modifier
                        .onSizeChanged {
                            sliderHeight = it.height
                        },
                    buffedPercentage = buffedPercentage,
                    currentTime = { currentTime().toFloat() },
                    totalTime = { totalTime().toFloat() },
                    onTimeChanged = onTimeChanged,
                )
            }
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
        contentAlignment = Alignment.Center,
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
                false -> R.drawable.video_play
                true -> R.drawable.video_pause
            },
            contentScale = ContentScale.None,
        )
    }
}

@Composable
internal fun VideoSlider(
    modifier: Modifier,
    buffedPercentage: () -> Int,
    currentTime: () -> Float,
    totalTime: () -> Float,
    onTimeChanged: (Float) -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        BottomSlider(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            value = buffedPercentage().toFloat(),
            enabled = false,
            colors = bufferSliderColors(),
            onValueChanged = {},
        )
        BottomSlider(
            modifier = Modifier.align(alignment = Alignment.Center),
            value = currentTime(),
            range = 0f..totalTime(),
            onValueChanged = onTimeChanged,
        )
    }
}

/*
* millisecond 를 MM:SS 로 변환합니다.
* */
private fun Long.formatMinSec(): String {
    return if (this == 0L) {
        "..."
    } else {
        String.format(
            Locale.getDefault(),
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(this),
                    ),
        )
    }
}
