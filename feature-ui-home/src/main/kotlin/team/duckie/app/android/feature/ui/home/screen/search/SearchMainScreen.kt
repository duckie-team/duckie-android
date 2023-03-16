/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

private val SearchScreenHorizontalPaddingDp = 16.dp

@Composable
internal fun SearchMainScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = activityViewModel(),
) {
    val state = vm.collectAsState().value

    LaunchedEffect(Unit) {
        vm.fetchPopularTags()
        vm.fetchRecentExam()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = QuackColor.White.composeColor,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(
                    horizontal = 16.dp,
                    vertical = 6.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackImage(
                src = QuackIcon.Search,
                size = DpSize(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = QuackColor.Gray4.composeColor,
                        shape = RoundedCornerShape(
                            size = 8.dp,
                        ),
                    )
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        vm.navigateToSearch()
                    }
                    .padding(start = 12.dp)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                QuackBody1(
                    text = stringResource(id = R.string.try_search),
                    color = QuackColor.Gray2,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        QuackTitle2(
            modifier = Modifier.padding(start = SearchScreenHorizontalPaddingDp),
            text = stringResource(id = R.string.popular_tag),
        )
        Spacer(modifier = Modifier.height(8.dp))
        // TODO(limsaehyun): 추후 꽥꽥에서, 전체 너비만큼 태그 Composable 을 넣을 수 있는 Composable 적용 필요
        QuackLazyVerticalGridTag(
            modifier = Modifier.padding(horizontal = SearchScreenHorizontalPaddingDp),
            items = state.popularTags.map { it.name },
            tagType = QuackTagType.Round,
            onClick = { index ->
                vm.navigateToSearch(searchTag = state.popularTags[index].name)
            },
            itemChunkedSize = 5,
        )
    }
}
