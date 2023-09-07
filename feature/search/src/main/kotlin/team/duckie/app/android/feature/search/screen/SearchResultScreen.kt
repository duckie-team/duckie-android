/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.DuckExamSmallCover
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.content.UserFollowingLayout
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.feature.search.R
import team.duckie.app.android.feature.search.constants.SearchResultStep
import team.duckie.app.android.feature.search.viewmodel.SearchViewModel
import team.duckie.app.android.feature.search.viewmodel.state.SearchState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackTab
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun SearchResultScreen(
    modifier: Modifier = Modifier,
    vm: SearchViewModel = activityViewModel(),
    navigateDetail: (Int) -> Unit,
    openBottomSheet: (Int) -> Unit,
    onSearchComplete: () -> Unit,
) {
    val state = vm.collectAsState().value

    val searchUsers = vm.searchUsers.collectAsLazyPagingItems()
    val searchExams = vm.searchExams.collectAsLazyPagingItems()

    val tabTitles = remember {
        SearchResultStep.values().map { step ->
            step.title
        }.toPersistentList()
    }

    LaunchedEffect(key1 = Unit) {
        onSearchComplete()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection()),
    ) {
        QuackTab(index = state.tagSelectedTab.index) {
            tabTitles.fastForEach { label ->
                tab(label) { index ->
                    vm.updateSearchResultTab(SearchResultStep.toStep(index))
                }
            }
        }
        when (state.tagSelectedTab) {
            SearchResultStep.DuckExam -> {
                SearchResultForExam(
                    searchExams = searchExams,
                    navigateDetail = navigateDetail,
                    onMoreClick = openBottomSheet,
                )
            }

            SearchResultStep.User -> SearchResultForUser(
                searchUsers = searchUsers,
                onClickFollow = { userId, follow ->
                    vm.followUser(
                        userId = userId,
                        isFollowing = follow,
                    )
                },
                myUserId = state.me?.id ?: 1,
                onClickUserProfile = vm::clickUserProfile,
            )
        }
        Spacer(space = 48.dp)
    }
}

@Composable
private fun SearchResultForUser(
    searchUsers: LazyPagingItems<SearchState.SearchUser>,
    onClickFollow: (Int, Boolean) -> Unit,
    onClickUserProfile: (Int) -> Unit,
    myUserId: Int,
) {
    if (searchUsers.itemCount == 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuackText(
                text = stringResource(id = R.string.no_search_user),
                typography = QuackTypography.HeadLine1.change(
                    color = QuackColor.Gray1,
                ),
            )
            Spacer(space = 12.dp)
            QuackText(
                text = stringResource(id = R.string.search_another_keyword),
                typography = QuackTypography.Body1.change(
                    color = QuackColor.Gray1,
                ),
            )
        }
    } else {
        LazyColumn {
            items(searchUsers) { item ->
                UserFollowingLayout(
                    userId = item?.userId ?: 0,
                    profileImgUrl = item?.profileImgUrl ?: "",
                    nickname = item?.nickname ?: "",
                    favoriteTag = item?.favoriteTag ?: "",
                    tier = item?.tier ?: "",
                    isFollowing = item?.isFollowing ?: false,
                    onClickTrailingButton = { follow ->
                        onClickFollow(item?.userId ?: 0, follow)
                    },
                    visibleTrailingButton = myUserId != item?.userId,
                    onClickUserProfile = onClickUserProfile,
                )
            }
        }
    }
}

@Composable
private fun SearchResultForExam(
    searchExams: LazyPagingItems<Exam>,
    navigateDetail: (Int) -> Unit,
    onMoreClick: (Int) -> Unit,
) {
    if (searchExams.itemCount == 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuackText(
                text = stringResource(id = R.string.no_search_exam),
                typography = QuackTypography.HeadLine1.change(
                    color = QuackColor.Gray1,
                ),
            )
            Spacer(space = 12.dp)
            QuackText(
                text = stringResource(id = R.string.search_another_keyword),
                typography = QuackTypography.Body1.change(
                    color = QuackColor.Gray1,
                ),
            )
        }
    } else {
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 20.dp),
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = SearchHorizontalPadding,
        ) {
            items(searchExams.itemCount) { index ->
                searchExams[index].let { exam ->
                    DuckExamSmallCover(
                        duckTestCoverItem = DuckTestCoverItem(
                            testId = exam?.id ?: 0,
                            thumbnailUrl = exam?.thumbnailUrl,
                            nickname = exam?.user?.nickname ?: "",
                            title = exam?.title ?: "",
                            solvedCount = exam?.solvedCount ?: 0,
                            heartCount = exam?.heartCount ?: 0,
                        ),
                        onItemClick = {
                            navigateDetail(exam?.id ?: 0)
                        },
                        onMoreClick = {
                            onMoreClick(exam?.id ?: 0)
                        },
                    )
                }
            }
        }
    }
}
