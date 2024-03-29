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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.LinearProgressBar
import team.duckie.app.android.common.compose.ui.icon.v2.Clock
import team.duckie.app.android.common.compose.ui.quack.todo.QuackTopAppBar
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackSubtitle2

@Composable
internal fun CloseAndPageTopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    currentPage: Int,
    totalPage: Int,
) {
    QuackTopAppBar(
        modifier = modifier,
        leadingIcon = QuackIcon.Outlined.Close,
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
            .padding(bottom = 16.dp),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Outlined.Close,
            onLeadingIconClick = onCloseClick,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            LinearProgressBar(
                progress = progress(),
            )
            QuackIcon(
                modifier = Modifier.background(QuackColor.White.value),
                icon = QuackIcon.Clock,
                size = 16.dp,
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
        QuackText(
            text = " / ",
            typography = QuackTypography.Subtitle2.change(color = QuackColor.Gray2),
        )
        QuackText(
            text = totalPage.toString(),
            typography = QuackTypography.Subtitle2.change(color = QuackColor.Gray2),
        )
    }
}
