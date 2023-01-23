/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.solve.problem.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.feature.ui.solve.problem.SolveProblemActivity
import team.duckie.app.android.feature.ui.solve.problem.answer.answerSection
import team.duckie.app.android.feature.ui.solve.problem.common.CloseAndPageTopBar
import team.duckie.app.android.feature.ui.solve.problem.common.DoubleButtonBottomBar
import team.duckie.app.android.feature.ui.solve.problem.dummyList
import team.duckie.app.android.feature.ui.solve.problem.question.questionSection
import team.duckie.app.android.util.ui.finishWithAnimation

@Composable
internal fun SolveProblemScreen() {
    val problems by remember { mutableStateOf(dummyList) }
    val totalPage = remember { problems.size }
    var currentPageIndex by remember { mutableStateOf(0) }
    val activity = LocalContext.current as SolveProblemActivity
    val answerSelections = remember {
        mutableStateListOf(
            elements = Array(
                size = problems.size,
                init = { -1 },
            ),
        )
    }
    val onNextPage: () -> Unit = remember {
        {
            if (currentPageIndex < totalPage - 1) {
                currentPageIndex = currentPageIndex.plus(1)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            CloseAndPageTopBar(
                onCloseClick = {
                    activity.finishWithAnimation()
                },
                currentPage = currentPageIndex + 1,
                totalPage = problems.size,
            )
        },
        bottomBar = {
            DoubleButtonBottomBar(
                isFirstPage = currentPageIndex == 0,
                onLeftButtonClick = {
                    currentPageIndex = currentPageIndex.minus(1)
                },
                onRightButtonClick = onNextPage,
            )
        },
    ) { padding ->
        Crossfade(
            modifier = Modifier.padding(padding),
            targetState = currentPageIndex,
        ) { pageIndex ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                questionSection(
                    page = pageIndex,
                    question = problems[pageIndex].question,
                )
                answerSection(
                    page = pageIndex,
                    answer = problems[pageIndex].answer,
                    answerSelections = answerSelections.toImmutableList(),
                    onClickAnswer = { index ->
                        answerSelections[pageIndex] = index
                    },
                    onSolveProblem = onNextPage,
                )
            }
        }
    }
}
