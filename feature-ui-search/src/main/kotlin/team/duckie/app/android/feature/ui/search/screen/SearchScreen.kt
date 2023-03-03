/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import kotlinx.collections.immutable.ImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.search.R
import team.duckie.app.android.feature.ui.search.viewmodel.SearchViewModel
import team.duckie.app.android.util.kotlin.AllowMagicNumber
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
            horizontalArrangement = Arrangement.Center,
        ) {
            QuackImage(
                src = QuackIcon.ArrowBack,
                size = DpSize(all = 24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            // TODO(limsaehyun): QuackQuack를 통해서 underline이 없는 TextField로 교체해야 함
            @AllowMagicNumber(because = "임시로 구현한 컴포넌트")
            QuackBasicTextField(
                modifier = Modifier.offset(y = (-4).dp),
                text = state.searchKeyword,
                onTextChanged = { keyword ->
                    vm.updateSearchKeyword(keyword = keyword)
                },
                placeholderText = stringResource(id = R.string.try_search),
            )
        }
        LazyColumn {
            recentKeywordSection(
                tags = state.recentSearch,
                onClickedClearAll = {
                    vm.clearAllRecentSearch()
                },
                onClickedClear = { tagId ->
                    vm.clearRecentSearch(tagId = tagId)
                },
            )
        }
    }
}

/**
 * 추천 검색어를 나타내는 Section
 */
@Suppress("UnusedPrivateMember") // TODO(limsaehyun): 추후에 추천 검색어 기능에 사용 예정
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
            keyword = tag.name,
            onCloseClicked = {
                onClickedClear(tag.id)
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
