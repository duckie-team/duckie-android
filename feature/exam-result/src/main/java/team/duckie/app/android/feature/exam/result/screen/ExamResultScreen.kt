/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.common.LoadingIndicator
import team.duckie.app.android.feature.exam.result.common.ResultBottomBar
import team.duckie.app.android.feature.exam.result.screen.exam.ExamResultContent
import team.duckie.app.android.feature.exam.result.screen.quiz.QuizResultContent
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultViewModel
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun ExamResultScreen(
    viewModel: ExamResultViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            QuackTopAppBar(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 16.dp),
                leadingIcon = QuackIcon.ArrowBack,
                onLeadingIconClick = viewModel::exitExam,
            )
        },
        bottomBar = {
            if ((state is ExamResultState.Loading).not()) {
                ResultBottomBar(
                    isQuiz = state.isQuiz(),
                    onClickRetryButton = {
                        viewModel.clickRetry()
                    },
                    onClickExitButton = viewModel::exitExam,
                )
            }
        },
    ) { padding ->
        when (state) {
            is ExamResultState.Loading -> {
                LoadingIndicator()
            }

            is ExamResultState.Success -> {
                with(state as ExamResultState.Success) {
                    if (isQuiz) {
                        QuizResultContent(
                            modifier = Modifier.padding(padding),
                            nickname = nickname,
                            resultImageUrl = reportUrl,
                            correctProblemCount = correctProblemCount,
                            time = time,
                            mainTag = mainTag,
                            ranking = ranking,
                            message = if (isPerfectScore) {
                                stringResource(
                                    id = R.string.exam_result_correct_problem_all,
                                    mainTag,
                                )
                            } else {
                                wrongAnswerMessage
                            },
                            reaction = reaction,
                            onReactionChanged = viewModel::updateReaction,
                        )
                    } else {
                        ExamResultContent(
                            resultImageUrl = reportUrl,
                        )
                    }
                }
            }

            is ExamResultState.Error -> {
                ErrorScreen(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    false,
                    onRetryClick = viewModel::initState,
                )
            }
        }
    }
}

private fun ExamResultState.isQuiz() = this is ExamResultState.Success && this.isQuiz
