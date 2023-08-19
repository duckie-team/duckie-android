/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.quack.todo.QuackReactionTextArea
import team.duckie.app.android.common.compose.ui.quack.todo.QuackTopAppBar
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.common.ResultBottomBar
import team.duckie.app.android.feature.exam.result.screen.exam.ExamResultContent
import team.duckie.app.android.feature.exam.result.screen.quiz.QuizResultContent
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultScreen
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.icon.quackicon.outlined.Share
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackHeadLine1

internal const val RANKER_THRESHOLD = 10

@Composable
internal fun ExamResultScreen(
    viewModel: ExamResultViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        when (state) {
            is ExamResultState.Loading -> {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                )
            }

            is ExamResultState.Success -> {
                val currentState = state as ExamResultState.Success

                DuckieDialog(
                    container = {
                        ExamResultReactionDialogInternal(
                            nickname = currentState.nickname,
                            mainTag = currentState.mainTag,
                            ranking = currentState.ranking,
                            reaction = currentState.reaction,
                            onReactionChanged = viewModel::updateReaction,
                        )
                    },
                    visible = currentState.isReactionValid,
                    onDismissRequest = {
                        viewModel.updateReactionDialogVisible(false)
                    },
                    leftButtonText = stringResource(id = R.string.cancel),
                    leftButtonOnClick = {
                        viewModel.updateReactionDialogVisible(false)
                    },
                    rightButtonText = stringResource(id = R.string.submit),
                    rightButtonOnClick = {
                        viewModel.postReaction()
                        viewModel.updateReactionDialogVisible(false)
                    },
                )

                when (currentState.currentScreen) {
                    ExamResultScreen.EXAM_RESULT -> ExamResultSuccessScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding(),
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
private fun ExamResultReactionDialogInternal(
    nickname: String,
    mainTag: String,
    ranking: Int,
    reaction: String,
    onReactionChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 12.dp,
        ),
    ) {
        QuackHeadLine1(
            modifier = Modifier.span(
                texts = listOf(nickname, mainTag, "${ranking}위"),
                style = SpanStyle(
                    color = QuackColor.DuckieOrange.value,
                    fontWeight = FontWeight.Bold,
                ),
            ),
            text = stringResource(
                id = if (ranking <= RANKER_THRESHOLD) {
                    R.string.exam_result_finish_title_ranker
                } else {
                    R.string.exam_result_finish_title_etc
                },
                nickname,
                mainTag,
                ranking,
            ),
        )
        Spacer(space = 12.dp)
        QuackReactionTextArea(
            reaction = reaction,
            onReactionChanged = onReactionChanged,
        )
    }
}

@Composable
private fun ExamResultSuccessScreen(
    modifier: Modifier = Modifier,
    state: ExamResultState.Success,
    viewModel: ExamResultViewModel,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            QuackTopAppBar(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 16.dp),
                leadingIcon = QuackIcon.Outlined.Close,
                onLeadingIconClick = viewModel::exitExam,
                trailingIcon = QuackIcon.Outlined.Share,
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
                        nickname = nickname,
                        myAnswer = myAnswer,
                        equalAnswerCount = equalAnswerCount,
                        profileImg = profileImg,
                        wrongComments = otherComments,
                        myComment = wrongComment,
                        myCommentChanged = viewModel::updateWrongComment,
                        onHeartComment = { isLike ->
                            viewModel.heartWrongComment(isLike)
                        },
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

@Preview
@Composable
fun Preview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackIcon(
            icon = QuackIcon.Outlined.Close,
            modifier = Modifier
                .quackClickable(
                    rippleEnabled = true,
                    onClick = {},
                )
                .padding(8.dp)
                .size(40.dp)

        )
    }
}
