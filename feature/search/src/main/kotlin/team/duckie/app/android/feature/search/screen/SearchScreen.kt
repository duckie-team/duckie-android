/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.search.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import kotlinx.collections.immutable.ImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.search.R
import team.duckie.app.android.feature.search.viewmodel.SearchViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun SearchScreen(
    vm: SearchViewModel,
) {
    LaunchedEffect(Unit) {
        vm.getRecentSearch()
    }

    val state = vm.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        Spacer(modifier = Modifier.height(22.dp))
        if (state.recentSearch.isEmpty()) {
            RecentSearchNotFoundScreen()
        } else {
            LazyColumn {
                recentKeywordSection(
                    tags = state.recentSearch,
                    onClickedClearAll = {
                        vm.clearAllRecentSearch()
                    },
                    onClickedClear = { keyword ->
                        vm.clearRecentSearch(keyword = keyword)
                    },
                    navigateToResult = { keyword ->
                        vm.updateSearchKeyword(
                            keyword = keyword,
                            debounce = false,
                        )
                    },
                )
            }
        }
    }
}

/**
 * 최근 검색어가 없을 경우 표시되는 화면
 * TODO(limsaehyun): 디자인 변경 예정
 * */
@Composable
private fun RecentSearchNotFoundScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        QuackHeadLine1(
            text = stringResource(id = R.string.no_recent_search),
            color = QuackColor.Gray1,
        )
        Spacer(modifier = Modifier.height(8.dp))
        QuackBody1(
            text = stringResource(id = R.string.search_another_keyword),
            color = QuackColor.Gray1,
        )
    }
}

/**
 * 추천 검색어를 나타내는 Section
 * TODO(limsaehyun): 추후에 추천 검색어 기능에 사용 예정
 */
@Suppress("UnusedPrivateMember")
private fun LazyListScope.recommendKeywordSection(
    tags: LazyPagingItems<Tag>,
//    keyword: String,
    onClickedSearch: () -> Unit,
) {
    items(tags) { tag ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .quackClickable {
                    onClickedSearch()
                },
            contentAlignment = Alignment.CenterStart,
        ) {
            QuackTitle2(text = tag?.name ?: "") // TODO(limsaehyun): QuackAnnotationTitle2 교체 필요
        }
    }
}

/**
 * 최신 검색어를 나타내는 Section
 */
private fun LazyListScope.recentKeywordSection(
    tags: ImmutableList<String>,
    navigateToResult: (String) -> Unit,
    onClickedClearAll: () -> Unit,
    onClickedClear: (String) -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SearchHorizontalPadding)
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackTitle2(text = stringResource(id = R.string.recent_search))
            Spacer(modifier = Modifier.weight(1f))
            QuackBody2(
                text = stringResource(id = R.string.clear_all),
                color = QuackColor.Gray1,
                onClick = {
                    onClickedClearAll()
                },
            )
        }
    }

    items(tags) { tag ->
        RecentSearchLayout(
            keyword = tag,
            onCloseClick = { keyword ->
                onClickedClear(keyword)
            },
        ) { keyword ->
            navigateToResult(keyword)
        }
    }
}

@Composable
private fun RecentSearchLayout(
    keyword: String,
    onCloseClick: (String) -> Unit,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(keyword) }
            .padding(SearchHorizontalPadding)
            .padding(vertical = 12.dp),
    ) {
        QuackImage(
            src = QuackIcon.Search,
            size = DpSize(16.dp),
            tint = QuackColor.Gray2,
        )
        Spacer(modifier = Modifier.width(8.dp))
        QuackBody1(text = keyword)
        Spacer(modifier = Modifier.weight(1f))
        QuackImage(
            src = QuackIcon.Close,
            size = DpSize(16.dp),
            tint = QuackColor.Gray2,
            onClick = { onCloseClick(keyword) },
        )
    }
}
