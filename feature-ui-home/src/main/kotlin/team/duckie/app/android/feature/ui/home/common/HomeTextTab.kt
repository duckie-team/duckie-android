/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackTitle2

@Composable
internal fun HomeTextTab(
    titles: PersistentList<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(titles) { index, text ->

            if (index == selectedTabIndex) {
                QuackHeadLine2(
                    text = text,
                    color = QuackColor.Black,
                    onClick = {
                        onTabSelected(index)
                    },
                )
            } else {
                QuackTitle2(
                    text = text,
                    color = QuackColor.Gray2,
                    onClick = {
                        onTabSelected(index)
                    },
                )
            }
        }
    }
}
