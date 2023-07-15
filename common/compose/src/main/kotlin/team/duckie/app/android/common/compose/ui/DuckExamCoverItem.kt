/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.ui.icon.v1.MoreId
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackTitle2

data class DuckTestCoverItem(
    val testId: Int,
    val thumbnailUrl: String?,
    val nickname: String,
    val title: String,
    val solvedCount: Int,
    val heartCount: Int,
)

private const val CoverRatio = 4f / 3f

@Composable
fun DuckExamSmallCover(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
    onMoreClick: (() -> Unit)? = null,
    isLoading: Boolean? = null,
) {
    DuckSmallCoverInternal(
        modifier = modifier.width(158.dp),
        duckTestCoverItem = duckTestCoverItem,
        onItemClick = onItemClick,
        onMoreClick = onMoreClick,
        isLoading = isLoading,
    )
}

@Composable
fun DuckExamSmallCoverForColumn(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
    onMoreClick: (() -> Unit)? = null,
    isLoading: Boolean? = null,
) {
    DuckSmallCoverInternal(
        modifier = modifier.fillMaxWidth(),
        duckTestCoverItem = duckTestCoverItem,
        onItemClick = onItemClick,
        onMoreClick = onMoreClick,
        isLoading = isLoading,
    )
}

@Composable
internal fun DuckSmallCoverInternal(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
    onMoreClick: (() -> Unit)? = null,
    isLoading: Boolean? = null,
) {
    Column(
        modifier = modifier
            .quackClickable(
                onClick = onItemClick,
                rippleEnabled = true,
            )
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.Start,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(CoverRatio)
                .clip(RoundedCornerShape(8.dp))
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            model = duckTestCoverItem.thumbnailUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody2(text = duckTestCoverItem.nickname)
            if (onMoreClick != null) {
                QuackImage(
                    src = QuackIcon.MoreId,
                    modifier = Modifier
                        .quackClickable(onClick = onMoreClick)
                        .size(DpSize(16.dp, 16.dp)),
                )
            }
        }
        QuackTitle2(
            modifier = Modifier
                .padding(top = 4.dp)
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            text = duckTestCoverItem.title,
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            QuackText(
                text = "${stringResource(id = R.string.examinee)} ${duckTestCoverItem.solvedCount}",
                typography = QuackTypography.Body2.change(color = QuackColor.Gray2),
            )
            QuackText(
                text = "·",
                typography = QuackTypography.Body2.change(color = QuackColor.Gray2),
            )
            QuackText(
                text = "${stringResource(id = R.string.heart)} ${duckTestCoverItem.heartCount}",
                typography = QuackTypography.Body2.change(color = QuackColor.Gray2),
            )
        }
    }
}
