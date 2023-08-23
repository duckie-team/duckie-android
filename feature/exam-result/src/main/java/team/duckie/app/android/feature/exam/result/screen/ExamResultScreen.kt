/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.exam.result.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.dialog.DuckieBottomSheetDialog
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.quack.todo.QuackReactionTextArea
import team.duckie.app.android.common.kotlin.getDiffDayFromToday
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.common.ResultBottomBar
import team.duckie.app.android.feature.exam.result.screen.exam.ExamResultContent
import team.duckie.app.android.feature.exam.result.screen.quiz.QuizResultContent
import team.duckie.app.android.feature.exam.result.screen.wronganswer.ChallengeCommentBottomSheetContent
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultScreen
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
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
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true, // TODO(limsaehyun) bottomContent가 보이지 않은 현상으로 인해 불가능
    )
    val coroutineScope = rememberCoroutineScope()

    DuckieBottomSheetDialog(
        sheetState = sheetState,
        sheetContent = {
            ChallengeCommentBottomSheetContent(
                fullScreen = false, // TODO(limsaehyun) sheetState 내부 swipeable에 접근이 불가능.. ModalBottomSheet을 직접 구현해야 함
                totalComments = state.commentsTotal,
                orderType = state.commentOrderType,
                onOrderTypeChanged = viewModel::transferCommentOrderType,
                myComment = state.myWrongComment,
                onMyCommentChanged = viewModel::updateWrongComment,
                comments = state.comments,
                onHeartComment = { commentId ->
                    viewModel.heartWrongComment(commentId)
                },
                myCommentCreateAt = state.commentCreateAt.getDiffDayFromToday(isShowSecond = false),
                isWriteComment = state.isWriteComment,
                onSendComment = {
                    viewModel.writeChallengeComment()
                },
                onDeleteComment = viewModel::deleteChallengeComment
            )
        },
        useHandle = true,
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
                            myAnswer = myAnswer,
                            profileImg = profileImg,
                            onHeartComment = { isLike ->
                                viewModel.heartWrongComment(isLike)
                            },
                            initialState = {
                                viewModel.getChallengeCommentList()
                            },
                            comments = state.comments,
                            commentsTotal = state.commentsTotal,
                            showCommentSheet = {
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            },
                            onDeleteComment = viewModel::deleteChallengeComment,
                            onIgnoreUser = viewModel::ignoreUser,
                            onReportComment = viewModel::reportChallengeComment,
                            mostWrongTotal = mostWrongAnswerTotal,
                            mostWrongData = mostWrongAnswerData,
                            equalAnswerCount = equalAnswerCount
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
}

private fun ExamResultState.isQuiz() = this is ExamResultState.Success && this.isQuiz
