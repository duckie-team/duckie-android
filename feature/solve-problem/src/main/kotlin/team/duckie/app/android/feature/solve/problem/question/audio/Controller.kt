/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question.audio

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.LinearProgressBar
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.app.android.feature.solve.problem.question.video.formatMinSec
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackBody3

@Composable
internal fun AudioController(
    modifier: Modifier,
    totalTime: Long,
    currentTime: Long,
    playState: PlayState,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackBody3(text = currentTime.formatMinSec())
            LinearProgressBar(progress = (totalTime / currentTime).toFloat())
            QuackBody3(text = totalTime.formatMinSec())
        }
        val (drawableId, clickEnabled) = when (playState) {
            PlayState.REQUIRE_PLAY -> {
                R.drawable.video_play to playState.enabled
            }

            PlayState.Playing -> {
                R.drawable.video_pause to playState.enabled
            }

            PlayState.REQUIRE_REPLAY -> {
                R.drawable.video_replay to playState.enabled
            }
        }
        QuackImage(
            modifier = Modifier.quackClickable(
                onClick = onClick.takeIf { clickEnabled }
            ),
            src = drawableId,
        )
    }
}

@Composable
internal fun PlayBox(
    startSecond: Int,
    endSecond: Int,
    @DrawableRes playButton: Int,
    onClick: () -> Unit,
) {
    //LinearProgressBar()
}
