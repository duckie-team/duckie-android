/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.viewall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import team.duckie.app.android.common.compose.getUniqueKey
import team.duckie.app.android.common.compose.itemsPagingKey
import team.duckie.app.android.common.compose.ui.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.common.compose.ui.DuckExamSmallCoverForColumn
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.feature.profile.R
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.mapper.toUiModel
import team.duckie.quackquack.material.QuackColor

private const val GRID_COUNT: Int = 2

/**
 * [GRID_COUNT]만큼 item 을 생성하여 [Arrangement.spacedBy]로 스크롤에 여백을 만듭니다.
 */
private fun LazyGridScope.spaceItem(
    maxIndex: Int,
    content: @Composable () -> Unit = {},
) {
    val count = maxIndex % GRID_COUNT + 1
    repeat(count) {
        item { content() }
    }
}

@Composable
fun ViewAllScreen(
    examType: ExamType,
    onBackPressed: () -> Unit,
    profileExams: LazyPagingItems<ProfileExam>,
    profileExamInstances: LazyPagingItems<ProfileExamInstance>,
    onItemClick: (DuckTestCoverItem) -> Unit,
    onMoreClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(QuackColor.White.value),
    ) {
        BackPressedHeadLine2TopAppBar(
            title = getViewAllTitle(examType = examType),
            onBackPressed = onBackPressed,
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            columns = GridCells.Fixed(GRID_COUNT),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            repeat(GRID_COUNT) {
                item {
                    Spacer(space = 20.dp)
                }
            }
            when (examType) {
                ExamType.Heart, ExamType.Created -> {
                    items(
                        count = profileExams.itemCount,
                        key = itemsPagingKey(
                            items = profileExams,
                            key = { profileExams[it]?.id?.getUniqueKey(it) },
                        ),
                    ) { index ->
                        profileExams[index]?.let { item ->
                            val duckTestCoverItem = item.toUiModel()
                            DuckExamSmallCoverForColumn(
                                modifier = Modifier.padding(bottom = 40.dp),
                                duckTestCoverItem = duckTestCoverItem,
                                onItemClick = { onItemClick(duckTestCoverItem) },
                                isLoading = profileExamInstances.loadState.append == LoadState.Loading,
                                onMoreClick = onMoreClick, // 추후 신고하기 구현 필요
                            )
                        }
                    }
                    spaceItem(profileExams.itemCount)
                }

                ExamType.Solved -> {
                    items(
                        count = profileExamInstances.itemCount,
                        key = itemsPagingKey(
                            items = profileExamInstances,
                            key = { profileExams[it]?.id?.getUniqueKey(it) },
                        ),
                    ) { index ->
                        profileExamInstances[index]?.let { item ->
                            val duckTestCoverItem = item.toUiModel()
                            DuckExamSmallCoverForColumn(
                                duckTestCoverItem = duckTestCoverItem,
                                onItemClick = { onItemClick(duckTestCoverItem) },
                                isLoading = profileExamInstances.loadState.append == LoadState.Loading,
                                onMoreClick = onMoreClick, // 추후 신고하기 구현 필요
                            )
                        }
                        if (index == profileExams.itemCount) {
                            Spacer(space = 40.dp)
                        }
                    }
                    spaceItem(profileExamInstances.itemCount)
                }
            }
        }
    }
}

@Composable
fun getViewAllTitle(examType: ExamType) = when (examType) {
    ExamType.Heart -> stringResource(id = R.string.hearted_exam)
    ExamType.Created -> stringResource(id = R.string.submitted_exam)
    ExamType.Solved -> stringResource(id = R.string.solved_exam)
}
