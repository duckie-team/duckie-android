/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.util.kotlin.runIf
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable

data class DuckTestCoverItem(
    val testId: Int,
    val thumbnailUrl: String?,
    val nickname: String,
    val title: String,
    val solvedCount: Int,
)

private const val CoverRatio = 4f / 3f

@Composable
fun DuckExamSmallCover(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
    isLoading: Boolean? = null,
) {
    DuckSmallCoverInternal(
        modifier = modifier.width(158.dp),
        duckTestCoverItem = duckTestCoverItem,
        onItemClick = onItemClick,
        isLoading = isLoading,
    )
}

@Composable
fun DuckExamSmallCoverForColumn(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
    isLoading: Boolean? = null,
) {
    DuckSmallCoverInternal(
        modifier = modifier.fillMaxWidth(),
        duckTestCoverItem = duckTestCoverItem,
        onItemClick = onItemClick,
        isLoading = isLoading,
    )
}

@Composable
internal fun DuckSmallCoverInternal(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onItemClick: () -> Unit,
    isLoading: Boolean? = null,
) {
    Column(
        modifier = modifier
            .quackClickable(rippleEnabled = true) {
                onItemClick()
            },
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
        QuackBody2(
            modifier = Modifier
                .padding(top = 8.dp)
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            text = duckTestCoverItem.nickname,
        )
        QuackTitle2(
            modifier = Modifier
                .padding(top = 4.dp)
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            text = duckTestCoverItem.title,
        )
        QuackBody2(
            modifier = Modifier
                .padding(top = 8.dp)
                .runIf(isLoading != null) { skeleton(isLoading!!) },
            text = "${stringResource(id = R.string.examinee)} ${duckTestCoverItem.solvedCount}",
        )
    }
}
