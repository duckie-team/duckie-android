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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
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
        Spacer(space = 20.dp)
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            when (examType) {
                ExamType.Heart, ExamType.Created -> {
                    items(
                        count = profileExams.itemCount,
                        key = itemsPagingKey(
                            items = profileExams,
                            key = { profileExams[it]?.id },
                        ),
                    ) { index ->
                        profileExams[index]?.let { item ->
                            val duckTestCoverItem = item.toUiModel()
                            DuckExamSmallCoverForColumn(
                                duckTestCoverItem = duckTestCoverItem,
                                onItemClick = { onItemClick(duckTestCoverItem) },
                                isLoading = profileExamInstances.loadState.append == LoadState.Loading,
                                onMoreClick = onMoreClick, // 추후 신고하기 구현 필요
                            )
                        }
                    }
                }

                ExamType.Solved -> {
                    items(
                        count = profileExamInstances.itemCount,
                        key = itemsPagingKey(
                            items = profileExamInstances,
                            key = { profileExamInstances[it]?.id },
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
                    }
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
