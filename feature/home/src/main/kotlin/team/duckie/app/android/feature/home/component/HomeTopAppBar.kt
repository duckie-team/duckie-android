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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import okhttp3.internal.immutableListOf
import team.duckie.app.android.common.compose.ui.TextTabLayout
import team.duckie.app.android.feature.home.R
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.material.QuackColor as QuackV2Color

@Suppress("UnusedPrivateMember") // 시험 생성하기를 추후에 다시 활용하기 위함
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
//      TODO(limsaehyun): 시험 생성하기가 가능한 스펙에서 활용
//      QuackImage(
//          src = QuackIcon.Create,
//          onClick = onClickedCreate,
//          size = HomeIconSize,
//      )

        QuackImage(
            src = R.drawable.home_ic_notice,
            modifier = Modifier
                .quackClickable(onClick = onClickedNotice)
                .size(DpSize(24.dp, 24.dp)),
        )
    }
}
