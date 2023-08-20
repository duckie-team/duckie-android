/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class,
)

package team.duckie.app.android.feature.solve.problem.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.isCurrentPage
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Problem.Companion.isSubjective
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.app.android.feature.solve.problem.answer.AnswerSection
import team.duckie.app.android.feature.solve.problem.common.ButtonBottomBar
import team.duckie.app.android.feature.solve.problem.common.TextFieldMargin
import team.duckie.app.android.feature.solve.problem.common.TimerTopBar
import team.duckie.app.android.feature.solve.problem.common.verticalScrollModifierAsCondition
import team.duckie.app.android.feature.solve.problem.question.QuestionSection
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.solve.problem.viewmodel.state.SolveProblemState

private const val QuizTopAppBarLayoutId = "QuizTopAppBar"
private const val QuizContentLayoutId = "QuizContent"
private const val QuizBottomBarLayoutId = "QuizBottomBar"

@Composable
internal fun QuizScreen(
    state: SolveProblemState,
    pagerState: PagerState,
    progress: () -> Float,
    stopExam: () -> Unit,
    finishQuiz: (Int, Boolean, String) -> Unit,
    onNextPage: (Int, InputAnswer, Int) -> Unit,
    startTimer: (Float) -> Unit,
) {
    val totalPage = remember { state.totalPage }
    val coroutineScope = rememberCoroutineScope()
    var examExitDialogVisible by remember { mutableStateOf(false) }

    val timeOver by remember {
        derivedStateOf { progress() < 0f }
    }

    val inputAnswers = remember {
        mutableStateListOf(
            elements = Array(
                size = state.quizProblems.size,
                init = { InputAnswer() },
            ),
        )
    }

    LaunchedEffect(pagerState.targetPage) {
        startTimer(state.time.toFloat())
    }

    LaunchedEffect(timeOver) {
        if (timeOver) {
            onNextPage(
                pagerState.currentPage,
                inputAnswers[pagerState.currentPage],
                totalPage - 1,
            )
        }
    }

    BackHandler {
        examExitDialogVisible = true
    }

    DuckieDialog(
        title = stringResource(id = R.string.quit_exam),
        message = stringResource(id = R.string.not_saved),
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { examExitDialogVisible = false },
        rightButtonText = stringResource(id = R.string.quit),
        rightButtonOnClick = stopExam,
        visible = examExitDialogVisible,
        onDismissRequest = { examExitDialogVisible = false },
    )

    Layout(
        content = {
            TimerTopBar(
                modifier = Modifier.layoutId(QuizTopAppBarLayoutId),
                onCloseClick = {
                    examExitDialogVisible = true
                },
                progress = progress,
            )
            ContentSection(
                modifier = Modifier.layoutId(QuizContentLayoutId),
                pagerState = pagerState,
                state = state,
                inputAnswers = inputAnswers.toImmutableList(),
                updateInputAnswers = { index, answer ->
                    inputAnswers[index] = answer
                },
            )
            ButtonBottomBar(
                modifier = Modifier
                    .layoutId(QuizBottomBarLayoutId)
                    .fillMaxWidth(),
                isLastPage = pagerState.currentPage == totalPage - 1,
                enabled = inputAnswers[pagerState.currentPage].answer.isNotEmpty(),
                onRightButtonClick = {
                    coroutineScope.launch {
                        val maximumPage = totalPage - 1
                        if (pagerState.currentPage == maximumPage) {
                            finishQuiz(
                                pagerState.currentPage,
                                true,
                                inputAnswers[pagerState.currentPage].answer,
                            )
                        } else {
                            onNextPage(
                                pagerState.currentPage,
                                inputAnswers[pagerState.currentPage],
                                maximumPage,
                            )
                        }
                    }
                },
            )
        },
        measurePolicy = screenMeasurePolicy(
            topLayoutId = QuizTopAppBarLayoutId,
            contentLayoutId = QuizContentLayoutId,
            bottomLayoutId = QuizBottomBarLayoutId,
        ),
    )
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    state: SolveProblemState,
    inputAnswers: ImmutableList<InputAnswer>,
    updateInputAnswers: (page: Int, inputAnswer: InputAnswer) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var textFieldHeight by remember { mutableStateOf(Dp.Unspecified) }
    val density = LocalDensity.current

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
    ) { pageIndex ->
        val problem = state.quizProblems[pageIndex]

        val requestFocus by remember(key1 = pagerState.currentPage) {
            derivedStateOf { pagerState.isCurrentPage(pageIndex) }
        }

        LaunchedEffect(key1 = requestFocus) {
            if (!problem.isSubjective()) {
                keyboardController?.hide()
            }
        }

        val isImageChoice = problem.answer?.isImageChoice == true
        val isRequireFlexibleImage = problem.isSubjective() && problem.question.isImage()

        Column(
            modifier = Modifier
                .verticalScrollModifierAsCondition(isImageChoice)
                .fillMaxSize()
        ) {
            Spacer(space = 16.dp)
            QuestionSection(
                page = pageIndex,
                question = problem.question,
                isImageChoice = isImageChoice,
                isRequireFlexibleImage = isRequireFlexibleImage,
                spaceImageToKeyboard = textFieldHeight + TextFieldMargin.Vertical,
            )
            val answer = problem.answer
            AnswerSection(
                pageIndex = pageIndex,
                answer = when (answer) {
                    is Answer.Short -> Answer.Short(
                        problem.correctAnswer
                            ?: duckieResponseFieldNpe("null 이 되면 안됩니다."),
                    )

                    is Answer.Choice, is Answer.ImageChoice -> answer
                    else -> duckieResponseFieldNpe("해당 분기로 빠질 수 없는 AnswerType 입니다.")
                },
                inputAnswers = inputAnswers,
                updateInputAnswers = updateInputAnswers,
                requestFocus = requestFocus,
                keyboardController = keyboardController,
                onShortAnswerSizeChanged = {
                    textFieldHeight = with(density) {
                        it.height.toDp()
                    }
                },
            )
            Spacer(space = 16.dp)
        }
    }
}
