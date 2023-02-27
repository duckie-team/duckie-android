/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.collections.immutable.ImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.feature.ui.search.viewmodel.SearchViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun SearchScreen(
    vm: SearchViewModel,
) {
    val state = vm.collectAsState().value

    val paging = state.searchResultTag.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackImage(
                src = QuackIcon.ArrowBack,
                size = DpSize(all = 24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            QuackBasicTextField(
                text = state.searchKeyword,
                onTextChanged = { keyword ->
                    vm.updateSearchKeyword(keyword = keyword)
                },
                placeholderText = "관심있는 키워드를 검색해보세요!",
            )
        }
        LazyColumn {
            if (state.searchKeyword.isEmpty()) {
                recentSearchSection(
                    tags = state.recentSearch,
                    onClickedClearAll = { },
                    onClickedClear = { tagId ->
                    },
                )
            } else {
                searchResultSection(
                    tags = paging,
                    keyword = state.searchKeyword,
                    onClickedSearch = {},
                )
            }
        }
    }
}

private fun LazyListScope.searchResultSection(
    tags: LazyPagingItems<Tag>,
    keyword: String,
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
            QuackTitle2(text = tag?.name ?: "") // TODO 디테일
        }
    }
}

private fun LazyListScope.recentSearchSection(
    tags: ImmutableList<Tag>,
    onClickedClearAll: () -> Unit,
    onClickedClear: (Int) -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackTitle2(text = "최근 검색")
            Spacer(modifier = Modifier.weight(1f))
            QuackBody2(
                text = "모두삭제",
                color = QuackColor.Gray1,
            )
        }
    }

    items(tags) { tag ->
        RecentSearchLayout(
            keyword = tag.name,
            onCloseClicked = {
            },
        )
    }
}

@Composable
private fun RecentSearchLayout(
    keyword: String,
    onCloseClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 12.dp),
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
            onClick = onCloseClicked,
        )
    }
}
