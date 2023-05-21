/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Clock
import team.duckie.app.android.common.compose.ui.LinearProgressBar
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun CloseAndPageTopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    currentPage: Int,
    totalPage: Int,
) {
    QuackTopAppBar(
        modifier = modifier,
        leadingIcon = QuackIcon.Close,
        onLeadingIconClick = onCloseClick,
        trailingContent = {
            PageInfo(
                currentPage = currentPage,
                totalPage = totalPage,
            )
        },
    )
}

@Composable
internal fun TimerTopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    progress: () -> Float,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            onLeadingIconClick = onCloseClick,
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            LinearProgressBar(
                progress = progress(),
            )
            QuackImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(QuackColor.White.composeColor),
                src = QuackIcon.Clock,
                size = DpSize(all = 16.dp),
            )
        }
    }
}

@Composable
private fun PageInfo(
    currentPage: Int,
    totalPage: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 2.dp),
    ) {
        QuackSubtitle2(text = currentPage.toString())
        QuackSubtitle2(
            text = " / ",
            color = QuackColor.Gray2,
        )
        QuackSubtitle2(
            text = totalPage.toString(),
            color = QuackColor.Gray2,
        )
    }
}
