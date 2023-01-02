/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.shared.ui.compose.TextTabLayout
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.util.DpSize

private val HomeIconSize = DpSize(24.dp)

@Composable
internal fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onClickedEdit: () -> Unit,
) {

    val homeTextTabTitles = persistentListOf(
        stringResource(id = R.string.recommend),
        stringResource(id = R.string.following),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextTabLayout(
            titles = homeTextTabTitles,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
        )
        QuackImage(
            src = R.drawable.home_ic_create_24,
            onClick = onClickedEdit,
            size = HomeIconSize,
        )
    }
}
