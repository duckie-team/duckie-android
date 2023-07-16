
/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.common.ResultBottomBar
import team.duckie.app.android.feature.exam.result.screen.exam.ExamResultContent
import team.duckie.app.android.feature.exam.result.screen.quiz.QuizResultContent
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultScreen
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

    QuackCrossfade(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        targetState = state,
    ) { currentState ->
        when (currentState) {
            is ExamResultState.Loading -> {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                )
            }

            is ExamResultState.Success -> {
                when (currentState.currentScreen) {
                    ExamResultScreen.EXAM_RESULT -> ExamResultSuccessScreen(
                        state = currentState,
                        viewModel = viewModel,
                    )

                    ExamResultScreen.SHARE_EXAM_RESULT -> ExamResultShareScreen(state = currentState) {
                        viewModel.updateExamResultScreen(ExamResultScreen.EXAM_RESULT)
                    }
                }
            }

            is ExamResultState.Error -> {
                ErrorScreen(
                    Modifier.fillMaxSize(),
                    false,
                    onRetryClick = viewModel::initState,
                )
            }
        }
    }
}

@Composable
private fun ExamResultSuccessScreen(
    state: ExamResultState.Success,
    viewModel: ExamResultViewModel,
) {
    Scaffold(
        topBar = {
            QuackTopAppBar(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 16.dp),
                leadingIcon = QuackIcon.Close,
                onLeadingIconClick = viewModel::exitExam,
                trailingIcon = QuackIcon.Share,
                onTrailingIconClick = {
                    viewModel.updateExamResultScreen(ExamResultScreen.SHARE_EXAM_RESULT)
                },
            )
        },
        bottomBar = {
            ResultBottomBar(
                isQuiz = state.isQuiz(),
                onClickRetryButton = {
                    viewModel.clickRetry()
                },
                onClickExitButton = viewModel::exitExam,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            with(state) {
                if (isQuiz) {
                    QuizResultContent(
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
                        isBestRecord = isBestRecord,
                        nickname = nickname,
                    )
                } else {
                    ExamResultContent(
                        resultImageUrl = reportUrl,
                    )
                }
            }
        }
    }
}

private fun ExamResultState.isQuiz() = this is ExamResultState.Success && this.isQuiz
