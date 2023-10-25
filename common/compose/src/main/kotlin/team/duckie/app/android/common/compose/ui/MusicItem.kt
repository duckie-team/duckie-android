/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.kotlin.addAmountString
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
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
fun GridMusicItem(
    modifier: Modifier = Modifier,
    exam: DuckTestCoverItem,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMore: (Int) -> Unit,
) = MusicItem(
    modifier = modifier.fillMaxWidth(),
    item = exam,
    onClickItem = onClickExam,
    onClickMore = onClickMore,
)

@Composable
fun RowMusicItem(
    modifier: Modifier = Modifier,
    exam: DuckTestCoverItem,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMore: (Int) -> Unit,
) = MusicItem(
    modifier = modifier.width(158.dp),
    item = exam,
    onClickItem = onClickExam,
    onClickMore = onClickMore,
)

@Composable
private fun MusicItem(
    modifier: Modifier = Modifier,
    item: DuckTestCoverItem,
    onClickItem: (DuckTestCoverItem) -> Unit,
    onClickMore: (Int) -> Unit,
) = with(item) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .quackClickable {
                onClickItem(item)
            },
    ) {
        Box(modifier = Modifier.height(158.dp)){
            QuackImage(
                modifier = Modifier.fillMaxSize(),
                src = thumbnailUrl,
                contentScale = ContentScale.FillBounds,
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
            progress = solvedCount.toFloat() / (totalCount?.toFloat() ?: 1f),
            color = QuackColor.DuckieOrange.value,
            backgroundColor = QuackColor.Gray4.value,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .padding(
                    start = 12.dp,
                    end = 4.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                QuackTitle2(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    singleLine = true,
                )
                Spacer(space = 4.dp)
                Row {
                    QuackBody2(text = solvedCount.toString())
                    QuackText(
                        typography = QuackTypography.Body2.change(
                            color = QuackColor.Gray1,
                        ),
                        text = "/${totalCount}".addAmountString(),
                    )
                }
            }
            QuackIcon(
                modifier = Modifier.quackClickable(
                    onClick = {
                        onClickMore(item.testId)
                    },
                ),
                icon = QuackIcon.Outlined.More,
                size = 16.dp,
            )
        }
    }
}
