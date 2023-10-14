/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import okhttp3.internal.immutableListOf
import team.duckie.app.android.common.compose.ui.TextTabLayout
import team.duckie.app.android.feature.home.R
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.QuackColor as QuackV2Color

@Suppress("unused") // 진행중 API 추가 작업을 위함
@Composable
internal fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onClickedCreate: () -> Unit,
    onClickedNotice: () -> Unit,
) {
    val context = LocalContext.current

    val homeTextTabTitles = remember {
        immutableListOf(
            context.getString(R.string.recommend),
            context.getString(R.string.proceed),
            context.getString(R.string.following),
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextTabLayout(
            titles = homeTextTabTitles.toImmutableList(),
            selectedTabStyle = QuackTypography.HeadLine1,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            tabStyle = QuackTypography.HeadLine1.change(color = QuackV2Color.Gray2),
        )

        // QuackImage(
        //     src = R.drawable.home_ic_notice,
        //     modifier = Modifier
        //         .quackClickable(onClick = onClickedNotice)
        //         .size(DpSize(24.dp, 24.dp)),
        // )
        //
        // // TODO(riflockle7): 임시로 활성화
        // QuackIcon(
        //     modifier = Modifier
        //         .quackClickable(onClick = onClickedCreate)
        //         .size(DpSize(24.dp, 24.dp)),
        //     icon = OutlinedGroup.Create,
        // )
    }
}
