/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.video

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
internal fun VideoPlayer(
    modifier: Modifier = Modifier,
    url: String
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri(url)
            )
            playWhenReady = true
            prepare()
        }
    }

    var totalDuration by remember { mutableStateOf(0L) }
    var currentTime by remember { mutableStateOf(0L) }
    var bufferedPercentage by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        val playerListener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                totalDuration = player.duration.coerceAtLeast(0L)
                currentTime = player.currentPosition.coerceAtLeast(0L)
                bufferedPercentage = player.bufferedPercentage
                progress = player.contentPosition
            }
        }

        exoPlayer.addListener(playerListener)

        onDispose {
            exoPlayer.removeListener(playerListener)
            exoPlayer.release()
        }
    }

    Box(modifier = modifier) {
        // Video View
        VideoView(
            context = context,
            videoPlayer = exoPlayer,
        )

        //Controller
        VideoController(
            modifier = Modifier.fillMaxSize(),
            isVisible = { true },
            isPlaying = { isPlaying },
            progress = { progress.toFloat() },
            onToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        exoPlayer.pause()
                    }

                    else -> {
                        exoPlayer.play()
                    }
                }
                isPlaying = !isPlaying
            },
            onProgressChanged = { progress ->
                exoPlayer.seekTo(progress.toLong())
            }
        )
    }
}

@Composable
internal fun VideoView(
    context: Context,
    videoPlayer: Player,
) {
    AndroidView(
        factory = {
            StyledPlayerView(context).apply {
                player = videoPlayer
                useController = false
                layoutParams =
                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
            }
        }
    )
}
