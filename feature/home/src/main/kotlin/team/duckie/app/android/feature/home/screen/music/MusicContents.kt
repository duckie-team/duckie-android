/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen.music

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.feature.home.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.More
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
fun TitleAndMusicContents(
    modifier: Modifier = Modifier,
    title: String,
    onClickShowAll: () -> Unit,
    musicItems: ImmutableList<MusicItemModel>,
    onClickMusicItem: (MusicItemModel) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackHeadLine1(text = title)
            QuackText(
                modifier = Modifier.quackClickable(onClick = onClickShowAll),
                typography = QuackTypography.Subtitle.change(
                    color = QuackColor.Gray1,
                ),
                text = stringResource(id = R.string.home_music_view_all_button_title),
            )
        }
        Spacer(space = 12.dp)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Spacer(space = 16.dp)
            }
            items(
                items = musicItems
            ) { item ->
                MusicItem(
                    musicItemModel = item,
                    onClickMusicItem = onClickMusicItem,
                )
            }
        }
    }
}

data class MusicItemModel(
    val id: Int,
    val thumbnail: String,
    val title: String,
    val onClickMore: (Int) -> Unit,
    val currentSolvedCount: Int,
    val totalSolvedCount: Int,
)


@Composable
private fun MusicItem(
    musicItemModel: MusicItemModel,
    onClickMusicItem: (MusicItemModel) -> Unit,
) = with(musicItemModel) {
    Column(
        modifier = Modifier
            .width(158.dp)
            .wrapContentHeight()
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .quackClickable {
                onClickMusicItem(musicItemModel)
            },
    ) {
        Box {
            QuackImage(
                modifier = Modifier.size(158.dp),
                src = thumbnail,
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(QuackColor.Black.change(alpha = 0.5f).value)
                    .align(Alignment.Center),
            ) {
                QuackImage(
                    modifier = Modifier.padding(10.dp),
                    src = R.drawable.home_video_play,
                )
            }
        }
        LinearProgressIndicator(
            progress = 0.5f,
            color = QuackColor.DuckieOrange.value,
            backgroundColor = QuackColor.Gray4.value,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                QuackTitle2(text = title)
                Spacer(space = 4.dp)
                Row {
                    QuackBody2(text = currentSolvedCount.toString())
                    QuackText(
                        typography = QuackTypography.Body2.change(
                            color = QuackColor.Gray1,
                        ),
                        text = "/${totalSolvedCount}개",
                    )
                }
            }
            QuackIcon(
                modifier = Modifier.quackClickable(
                    onClick = {
                        onClickMore(id)
                    },
                ),
                icon = QuackIcon.Outlined.More,
                size = 16.dp,
            )
        }
    }
}
