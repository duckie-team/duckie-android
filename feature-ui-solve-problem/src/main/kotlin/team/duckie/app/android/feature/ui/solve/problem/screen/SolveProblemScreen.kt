/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.solve.problem.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.feature.ui.solve.problem.SolveProblemActivity
import team.duckie.app.android.feature.ui.solve.problem.answer.answerSection
import team.duckie.app.android.feature.ui.solve.problem.common.CloseAndPageTopBar
import team.duckie.app.android.feature.ui.solve.problem.common.DoubleButtonBottomBar
import team.duckie.app.android.feature.ui.solve.problem.question.questionSection
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.util.ui.finishWithAnimation

@ExperimentalFoundationApi
@Composable
internal fun SolveProblemScreen(
    viewModel: SolveProblemViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val totalPage = remember { state.totalPage }
    val activity = LocalContext.current as SolveProblemActivity
    val pagerState = rememberPagerState()

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
                currentPage = pagerState.currentPage + 1,
                totalPage = totalPage,
            )
        },
        bottomBar = {
            DoubleButtonBottomBar(
                isFirstPage = pagerState.currentPage == 0,
                onLeftButtonClick = viewModel::movePreviousPage,
                onRightButtonClick = viewModel::moveNextPage,
            )
        },
    ) { padding ->
        HorizontalPager(
            contentPadding = padding,
            pageCount = state.totalPage,
            state = pagerState,
        ) { pageIndex ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                questionSection(
                    page = pageIndex,
                    question = state.problems[pageIndex].problem.question,
                )
                val answer = state.problems[pageIndex].problem.answer
                answerSection(
                    page = pageIndex,
                    answer = when (answer) {
                        is Answer.Short -> Answer.Short(
                            state.problems[pageIndex].problem.correctAnswer
                                ?: duckieResponseFieldNpe("null 이 되면 안됩니다."),
                        )

                        is Answer.Choice, is Answer.ImageChoice -> answer
                        else -> duckieResponseFieldNpe("해당 분기로 빠질 수 없는 AnswerType 입니다.")
                    },
                    inputAnswers = state.inputAnswers,
                    onClickAnswer = viewModel::inputAnswer,
                    onSolveProblem = viewModel::moveNextPage,
                )
            }
        }
    }
}
