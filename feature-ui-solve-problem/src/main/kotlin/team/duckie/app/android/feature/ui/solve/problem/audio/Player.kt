/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.audio

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView
import team.duckie.app.android.feature.ui.solve.problem.R

private val PlayingGifUrl =
    "https://s3-alpha-sig.figma.com/img/b797/6283/a4e5becc8777544c6f9489d48ac593c8?Expires=1675641600&Signature=mLfLrS5~d7rNyYp7bUfNmxbXteqczEvrXMjPVKFUHqh6ghNfVG0ojEgg8J1U7IDxRv1DQXt0ixhDqd~yqb~EmfdA8kIIcZaCiM1UhB1THYdppHcXNqCCAA6ZaVY-OnTe4AAXSlw78IMBUtz4sR9Xv8Fid8ZYnvaenUy8fQSN-LMLkcM8dzSzshThp-tvAwcZE-38PAy4iT2a4ebn4c5UMgELIdReMJTDSyKsnvywMx-bugaQGx0hD4Tm1kPMDBgFdfVe~BooZ5s99xokwzCFDINFYMR~iJcJswdGsONHm4Oz7YfeUx-CkigVlIxzlyFqc5w9xlgnQoYq5LP17Gl6hA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"

@Composable
internal fun AudioPlayer(
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
    var isPlaying by remember { mutableStateOf(false) }
    var repeatCount by remember { mutableStateOf(0) }
    var playBack by remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        val playerListener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                isPlaying = player.isPlaying
                playBack = player.playbackState
            }
        }

        exoPlayer.addListener(playerListener)

        onDispose {
            exoPlayer.removeListener(playerListener)
            exoPlayer.release()
        }
    }
    Box(){
        AudioView(
            context = context,
            videoPlayer = exoPlayer,
        )
        AudioController(
            modifier = Modifier,
            isPlaying = { isPlaying },
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
            buttonText = when (repeatCount) {
                0 -> stringResource(id = R.string.listen_question)
                else -> stringResource(id = R.string.listen_repeat)
            },
            gifUrl = PlayingGifUrl,
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
        }
    )
}
