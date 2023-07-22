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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.getPlaceable
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.common.ResultBottomBar
import team.duckie.app.android.feature.exam.result.screen.exam.ExamResultContent
import team.duckie.app.android.feature.exam.result.screen.quiz.QuizResultContent
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultScreen
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackHeadLine1

private const val RANKER_REACTION_MAX_LENGTH: Int = 40

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
                id = if (ranking <= 10) {
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
        QuizResultReactionTextArea(
            ranking = ranking,
            reaction = reaction,
            onReactionChanged = onReactionChanged,
        )
    }
}

private object QuizResultReactionTextAreaLayoutId {
    const val QuizReviewPlaceHolder = "QuizReviewPlaceHolder"
    const val QuizReviewTextArea = "QuizReviewTextArea"
    const val ReactionLimitText = "ReactionLimitText"
}

private fun getQuizResultReactionMeasurePolicy(
    placeholderVisible: Boolean = true,
) = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(width = true)

    with(QuizResultReactionTextAreaLayoutId) {
        val quizReviewPlaceHolderPlaceable =
            measurables.getPlaceable(QuizReviewPlaceHolder, looseConstraints)
        val quizReviewTextAreaPlaceable =
            measurables.getPlaceable(QuizReviewTextArea, extraLooseConstraints)
        val reactionLimitTextPlaceable =
            measurables.getPlaceable(ReactionLimitText, looseConstraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            quizReviewTextAreaPlaceable.place(x = 0, y = 0)
            if (placeholderVisible) {
                quizReviewPlaceHolderPlaceable.place(x = 0, y = 0)
            }
            reactionLimitTextPlaceable.place(
                x = constraints.maxWidth - reactionLimitTextPlaceable.width,
                y = quizReviewTextAreaPlaceable.height - reactionLimitTextPlaceable.height,
            )
        }
    }
}

@Composable
private fun QuizResultReactionTextArea(
    reaction: String,
    ranking: Int,
    onReactionChanged: (String) -> Unit,
) = with(QuizResultReactionTextAreaLayoutId) {
    Layout(
        modifier = Modifier.height(100.dp),
        measurePolicy = getQuizResultReactionMeasurePolicy(
            placeholderVisible = reaction.isEmpty(),
        ),
        content = {
            QuackReviewTextArea(
                modifier = Modifier
                    .layoutId(QuizReviewTextArea)
                    .fillMaxHeight(),
                text = reaction,
                onTextChanged = { str ->
                    if (str.length <= RANKER_REACTION_MAX_LENGTH) {
                        onReactionChanged(str)
                    }
                },
                placeholderText = "",
                focused = true,
            )
            QuackText(
                modifier = Modifier
                    .layoutId(QuizReviewPlaceHolder)
                    .padding(all = 16.dp),
                text = stringResource(
                    id = R.string.exam_result_ranked_text_area_hint,
                    ranking,
                ),
                typography = QuackTypography.Body1.change(
                    color = QuackColor.Gray2,
                ),
            )
            QuackText(
                modifier = Modifier
                    .layoutId(ReactionLimitText)
                    .padding(
                        bottom = 12.dp,
                        end = 12.dp,
                    ),
                text = "${reaction.length} / $RANKER_REACTION_MAX_LENGTH",
                typography = QuackTypography.Body1.change(
                    color = QuackColor.Gray2,
                ),
            )
        },
    )
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
