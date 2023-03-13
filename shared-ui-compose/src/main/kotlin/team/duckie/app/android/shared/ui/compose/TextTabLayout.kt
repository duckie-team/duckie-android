/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
fun TextTabLayout(
    titles: ImmutableList<String>,
    selectedTabStyle: QuackTextStyle = QuackTextStyle.HeadLine2,
    tabStyle: QuackTextStyle = QuackTextStyle.Title2,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    space: Dp = 12.dp,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(titles) { index, text ->
            if (index == selectedTabIndex) {
                QuackText(
                    modifier = Modifier.quackClickable {
                        onTabSelected(index)
                    },
                    text = text,
                    style = selectedTabStyle,
                )
            } else {
                QuackText(
                    modifier = Modifier.quackClickable {
                        onTabSelected(index)
                    },
                    text = text,
                    style = tabStyle,
                )
            }
        }
    }
}
