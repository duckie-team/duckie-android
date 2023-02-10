/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.solveproblem.question.video

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
internal fun VideoPlayer(
    modifier: Modifier = Modifier,
    url: String,
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri(url),
            )
            playWhenReady = true
            prepare()
        }
    }
    var totalTime by remember { mutableStateOf(0L) }
    var currentTime by remember { mutableStateOf(0L) }
    var bufferedPercentage by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var isControllerVisible by remember { mutableStateOf(false) }
    var playBack by remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        val playerListener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                isPlaying = player.isPlaying // 실행 중
                totalTime = player.duration.coerceAtLeast(0L) // 전체 시간 MS
                currentTime = player.currentPosition.coerceAtLeast(0L) // 현재 시간 MS
                bufferedPercentage = player.bufferedPercentage // Buffering 퍼센테이지
                playBack = player.playbackState
            }
        }

        exoPlayer.addListener(playerListener)

        onDispose {
            exoPlayer.removeListener(playerListener)
            exoPlayer.release()
        }
    }

    Box(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
    ) {
        VideoView(
            modifier = Modifier.fillMaxSize(),
            context = context,
            videoPlayer = exoPlayer,
            onPlayerClick = {
                isControllerVisible = isControllerVisible.not()
            },
        )
        VideoController(
            modifier = Modifier.fillMaxSize(),
            isVisible = { isControllerVisible },
            isPlaying = { isPlaying },
            totalTime = { totalTime },
            currentTime = { currentTime },
            buffedPercentage = { bufferedPercentage },
            onToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        exoPlayer.pause()
                    }

                    exoPlayer.isPlaying.not() && playBack == Player.STATE_ENDED -> {
                        exoPlayer.seekTo(0, 0)
                        exoPlayer.playWhenReady = true
                    }

                    else -> {
                        exoPlayer.play()
                    }
                }
                isPlaying = isPlaying.not()
            },
            onTimeChanged = { progress ->
                exoPlayer.seekTo(progress.toLong())
            },
        )
    }
}

@Composable
internal fun VideoView(
    modifier: Modifier = Modifier,
    context: Context,
    videoPlayer: Player,
    onPlayerClick: () -> Unit,
) {
    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                // TODO(EvergreenTree97) : QuackClickable의 rippleEnabled 적용되지 않음
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onPlayerClick,
            ),
        factory = {
            StyledPlayerView(context).apply {
                player = videoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                layoutParams =
                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
            }
        },
    )
}
