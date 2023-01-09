/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.viewmodel.SearchResultViewModel
import team.duckie.app.android.shared.ui.compose.DuckTestSmallCover
import team.duckie.app.android.shared.ui.compose.UserFollowingLayout
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

private val HomeTagListPadding = PaddingValues(
    top = 20.dp,
    start = 16.dp,
    end = 16.dp,
)

@Composable
internal fun SearchResultScreen(
    modifier: Modifier = Modifier,
    vm: SearchResultViewModel = activityViewModel(),
    onPrevious: () -> Unit,
) {
    val state = vm.collectAsState().value

    LaunchedEffect(Unit) {
        vm.fetchSearchResultForExam(state.searchTag)
        vm.fetchSearchResultForUser(state.searchTag)
    }

    val tabTitles = SearchResultStep.values().map {
        it.title
    }.toPersistentList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection()),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onLeadingIconClick = {
                onPrevious()
            },
            leadingText = state.searchTag,
        )
        QuackMainTab(
            titles = tabTitles,
            selectedTabIndex = state.tagSelectedTab.index,
            onTabSelected = { index ->
                vm.changeSearchResultTab(SearchResultStep.toStep(index))
            },
        )
        when (state.tagSelectedTab) {
            SearchResultStep.DuckTest -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState(),
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = HomeTagListPadding,
            ) {
                items(state.searchResultForTest) { item ->
                    DuckTestSmallCover(
                        duckTestCoverItem = item,
                        onClick = {
                            // TODO(limsaehyun): 상세보기로 이동
                        },
                    )
                }
            }
            SearchResultStep.User -> LazyColumn(
                contentPadding = HomeTagListPadding,
            ) {
                items(state.searchResultForUser) { item ->
                    UserFollowingLayout(
                        userId = item.userId,
                        profile = item.profile,
                        name = item.name,
                        examineeNumber = item.examineeNumber,
                        createAt = item.createAt,
                        isFollowing = item.isFollowing,
                        onClickFollowing = {
                            // TODO(limsaehyun): following request
                        },
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}
