/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
package team.duckie.app.android.feature.solve.problem.question.audio

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView

enum class PlayState(val enabled: Boolean) {
    REQUIRE_PLAY(true),
    Playing(false),
    REQUIRE_REPLAY(true),
}

@Composable
internal fun AudioPlayer(
    modifier: Modifier = Modifier,
    url: String,
) {
    val context = LocalContext.current
    val exoPlayer = remember(url) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri(url),
            )
            playWhenReady = true
            prepare()
        }
    }
    var isPlaying by remember { mutableStateOf(false) }
    var repeatCount by remember { mutableIntStateOf(0) }
    var playBack by remember { mutableIntStateOf(0) }
    var totalTime by remember { mutableLongStateOf(0L) }
    var currentTime by remember { mutableLongStateOf(0L) }

    DisposableEffect(Unit) {
        val playerListener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                isPlaying = player.isPlaying
                playBack = player.playbackState
                totalTime = player.duration.coerceAtLeast(0L)
                currentTime = player.currentPosition.coerceAtLeast(0L)
            }
        }

        exoPlayer.addListener(playerListener)

        onDispose {
            exoPlayer.removeListener(playerListener)
            exoPlayer.release()
        }
    }
    Box(modifier = modifier) {
        AudioView(
            context = context,
            videoPlayer = exoPlayer,
        )
        AudioController(
            modifier = Modifier,
            totalTime = totalTime,
            currentTime = currentTime,
            playState = if (repeatCount == 0 && isPlaying.not()) {
                PlayState.REQUIRE_PLAY
            } else if (isPlaying) {
                PlayState.Playing
            } else {
                PlayState.REQUIRE_REPLAY
            },
            onClick = {
                when {
                    exoPlayer.isPlaying.not() && playBack == Player.STATE_ENDED -> {
                        exoPlayer.playWhenReady = true
                        repeatCount++
                    }

                    else -> {
                        exoPlayer.play()
                    }
                }
                isPlaying = isPlaying.not()
            },
        )
    }
}

@Composable
internal fun AudioView(
    context: Context,
    videoPlayer: Player,
) {
    AndroidView(
        factory = {
            PlayerControlView(context).apply {
                player = videoPlayer
                visibility = View.GONE
            }
        },
    )
}
