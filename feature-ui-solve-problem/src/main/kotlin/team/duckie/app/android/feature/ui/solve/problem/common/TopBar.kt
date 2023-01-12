/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun CloseAndPageTopBar(
    onCloseClick: () -> Unit,
    currentPage: Int,
    totalPage: Int
) {
    QuackTopAppBar(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .padding(end = 16.dp),
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
            color = QuackColor.Gray2
        )
        QuackSubtitle2(
            text = totalPage.toString(),
            color = QuackColor.Gray2,
        )
    }
}
