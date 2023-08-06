/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.home.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.icon.v1.SearchId
import team.duckie.app.android.common.compose.ui.quack.todo.QuackLazyVerticalGridTag
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.viewmodel.MainViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2

private val SearchScreenHorizontalPaddingDp = 16.dp

@Composable
internal fun SearchMainScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    vm: MainViewModel = activityViewModel(),
) {
    val state = vm.collectAsState().value
    var isPullRefresh by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        initState(MainScreenType.Search) { vm.fetchPopularTags() }
    }

    @AllowMagicNumber val pullRefreshState = rememberPullRefreshState(
        refreshing = isPullRefresh,
        onRefresh = {
            isPullRefresh = true
            vm.fetchPopularTags()
            coroutineScope.launch {
                delay(1000L)
                isPullRefresh = false
            }
        },
    )

    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = QuackColor.White.value,
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
                    modifier = Modifier.size(DpSize(width = 24.dp, height = 24.dp)),
                    src = QuackIcon.SearchId,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .quackClickable(
                            rippleEnabled = false,
                            onClick = vm::navigateToSearch,
                        )
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(
                            color = QuackColor.Gray4.value,
                            shape = RoundedCornerShape(
                                size = 8.dp,
                            ),
                        )
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    QuackText(
                        text = stringResource(id = R.string.try_search),
                        typography = QuackTypography.Body1.change(
                            color = QuackColor.Gray2,
                        ),
                    )
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
            QuackTitle2(
                modifier = Modifier.padding(start = SearchScreenHorizontalPaddingDp),
                text = stringResource(id = R.string.popular_tag),
            )
            Spacer(modifier = Modifier.height(8.dp))
            // TODO(limsaehyun): 추후 꽥꽥에서, 전체 너비만큼 태그 Composable 을 넣을 수 있는 Composable 적용 필요
            QuackLazyVerticalGridTag(
                modifier = Modifier.padding(horizontal = SearchScreenHorizontalPaddingDp),
                items = state.popularTags.map { it.name },
                tagTypeResId = null,
                onClick = { index ->
                    vm.navigateToSearch(searchTag = state.popularTags[index].name)
                },
                itemChunkedSize = 5,
            )
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isPullRefresh,
            state = pullRefreshState,
        )
    }
}
