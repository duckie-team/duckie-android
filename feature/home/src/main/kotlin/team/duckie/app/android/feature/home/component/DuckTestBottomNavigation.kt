/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.component

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import okhttp3.internal.immutableListOf
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.feature.home.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText

/**
 * [DuckTestBottomNavigation] 를 그리는데 필요한 리소스들을 정의합니다.
 */
private object DuckTestBottomNavigationDefaults {
    val Height = 52.dp
    val BackgroundColor = QuackColor.White

    val IconSize = DpSize(width = 24.dp, height = 24.dp)
}

/**
 * [DuckTestBottomNavigation] 을 구현합니다.
 *
 * 각각 아이템들은 화면에 1:N 비율로 가로 길이가 결정됩니다.
 *
 * icon 은 defaultIcon 과 selectedIcon 을 data class 를 통해 둘 다 전달받고
 * selectedIndex 의 상태값과 비교하여 보여줘야하는 Icon 을 결정합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param selectedIndex 현재 선택되어있는 index 상태값
 * @param onClick BottomNavigation 의 클릭 이벤트
 */
@Composable
internal fun DuckTestBottomNavigation(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onClick: (index: Int) -> Unit,
): Unit = with(
    receiver = DuckTestBottomNavigationDefaults,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = Height,
            )
            .background(
                color = BackgroundColor.value,
            ),
    ) {
        rememberBottomNavigationIcons().fastForEachIndexed { index, icons ->
            Column(
                modifier = Modifier
                    .weight(
                        weight = 1f,
                    )
                    .fillMaxSize()
                    .quackClickable(
                        rippleEnabled = false,
                        onClick = {
                            onClick(index)
                        },
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                QuackImage(
                    modifier = Modifier.size(IconSize),
                    src = icons.pick(
                        isSelected = index == selectedIndex,
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                QuackText(
                    text = stringResource(id = icons.title),
                    typography = QuackTypography.Body2.change(
                        color = if (index == selectedIndex) {
                            QuackColor.Black
                        } else {
                            QuackColor.Gray1
                        },
                    ),
                )
            }
        }
    }
}

/**
 * [DuckTestBottomNavigation] 에서 사용되는 아이콘들을 반환해줍니다.
 *
 * @return [BottomNavigationIcon] 의 모음
 */
@Composable
private fun rememberBottomNavigationIcons() = remember {
    immutableListOf(
        BottomNavigationIcon(
            defaultIcon = R.drawable.home_ic_home_24,
            selectedIcon = R.drawable.home_ic_home_filled_24,
            title = R.string.home,
        ),
        BottomNavigationIcon(
            defaultIcon = R.drawable.home_ic_search_24,
            selectedIcon = R.drawable.home_ic_search_filled_24,
            title = R.string.search,
        ),
        BottomNavigationIcon(
            defaultIcon = R.drawable.home_ic_ranking_24,
            selectedIcon = R.drawable.home_ic_ranking_filled_24,
            title = R.string.ranking,
        ),
        BottomNavigationIcon(
            defaultIcon = R.drawable.home_ic_profile_24,
            selectedIcon = R.drawable.home_ic_profile_filled_24,
            title = R.string.mypage,
        ),
    )
}

/**
 * [DuckTestBottomNavigation] 에서 표시할 아이콘들을 정의합니다.
 *
 * @property defaultIcon 기본 아이콘 (선택 X)
 * @property selectedIcon 선택 상태의 아이콘
 */
private data class BottomNavigationIcon(
    @DrawableRes
    val defaultIcon: Int,
    @DrawableRes
    val selectedIcon: Int,
    @StringRes
    val title: Int,
) {
    /**
     * 주어진 상태에 맞는 아이콘 리소스를 가져옵니다.
     *
     * @param isSelected 현재 선택된 상태인지 여부
     *
     * @return [isSelected] 여부에 따라 사용할 [Icon]
     */
    @Stable
    fun pick(
        isSelected: Boolean,
    ) = when (isSelected) {
        true -> selectedIcon
        else -> defaultIcon
    }
}
