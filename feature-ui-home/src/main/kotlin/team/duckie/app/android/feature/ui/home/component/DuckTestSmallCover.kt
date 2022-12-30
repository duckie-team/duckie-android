/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.feature.ui.home.R
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable

data class DuckTestCoverItem(
    val testId: Int,
    val coverImg: String,
    val nickname: String,
    val title: String,
    val examineeNumber: Int,
)

@Composable
fun DuckTestSmallCover(
    modifier: Modifier = Modifier,
    duckTestCoverItem: DuckTestCoverItem,
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .quackClickable(
                rippleEnabled = false,
            ) {
                onClick(duckTestCoverItem.testId)
            }
    ) {
        AsyncImage(
            modifier = Modifier.size(158.dp, 116.dp),
            model = duckTestCoverItem.coverImg,
            contentDescription = null,
        )
        QuackBody2(
            modifier = Modifier.padding(top = 8.dp),
            text = duckTestCoverItem.nickname,
        )
        QuackTitle2(
            modifier = Modifier.padding(top = 4.dp),
            text = duckTestCoverItem.title,
        )
        QuackBody2(
            modifier = Modifier.padding(top = 8.dp),
            text = "${stringResource(id = R.string.examinee)} ${duckTestCoverItem.examineeNumber}",
        )
    }
}
