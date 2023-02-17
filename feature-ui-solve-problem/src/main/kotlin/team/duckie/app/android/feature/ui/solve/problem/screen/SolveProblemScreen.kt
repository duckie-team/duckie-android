/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.solve.problem.screen

import androidx.compose.animation.Crossfade
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
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.ui.finishWithAnimation

@Composable
internal fun SolveProblemScreen(
    viewModel: SolveProblemViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val totalPage = remember { state.totalPage }
    val activity = LocalContext.current as SolveProblemActivity

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
                currentPage = state.currentPageIndex + 1,
                totalPage = totalPage,
            )
        },
        bottomBar = {
            DoubleButtonBottomBar(
                isFirstPage = state.currentPageIndex == 0,
                onLeftButtonClick = viewModel::movePreviousPage,
                onRightButtonClick = viewModel::moveNextPage,
            )
        },
    ) { padding ->
        Crossfade(
            modifier = Modifier.padding(padding),
            targetState = state.currentPageIndex,
        ) { pageIndex ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                questionSection(
                    page = pageIndex,
                    question = state.problems[pageIndex].problem.question,
                )
                // TODO(riflockle7): problem 엔티티 commit
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
