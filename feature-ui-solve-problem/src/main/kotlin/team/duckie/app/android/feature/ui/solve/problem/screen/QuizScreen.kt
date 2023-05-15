/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
)

package team.duckie.app.android.feature.ui.solve.problem.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.feature.ui.solve.problem.R
import team.duckie.app.android.feature.ui.solve.problem.answer.answerSection
import team.duckie.app.android.feature.ui.solve.problem.common.ButtonBottomBar
import team.duckie.app.android.feature.ui.solve.problem.common.TimerTopBar
import team.duckie.app.android.feature.ui.solve.problem.question.questionSection
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.SolveProblemState
import team.duckie.app.android.shared.ui.compose.dialog.DuckieDialog
import team.duckie.app.android.util.compose.moveNextPage
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

private const val QuizTopAppBarLayoutId = "QuizTopAppBar"
private const val QuizContentLayoutId = "QuizContent"
private const val QuizBottomBarLayoutId = "QuizBottomBar"

@Composable
internal fun QuizScreen(
    state: SolveProblemState,
    progress: () -> Float,
    inputAnswer: (Int, InputAnswer) -> Unit,
    stopExam: () -> Unit,
    finishQuiz: (Int) -> Unit,
    onNextPage: (Int) -> Unit,
    startTimer: () -> Unit,
) {
    val totalPage = remember { state.totalPage }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var examExitDialogVisible by remember { mutableStateOf(false) }

    val timeOver by remember {
        derivedStateOf { progress() == 0f }
    }

    LaunchedEffect(pagerState.targetPage) {
        startTimer()
    }

    LaunchedEffect(timeOver) {
        if (timeOver) {
            finishQuiz(pagerState.currentPage)
        }
    }

    DuckieDialog(
        title = stringResource(id = R.string.quit_exam),
        message = stringResource(id = R.string.not_saved),
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { examExitDialogVisible = false },
        rightButtonText = stringResource(id = R.string.quit),
        rightButtonOnClick = { stopExam() },
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
                inputAnswer = inputAnswer,
            )
            ButtonBottomBar(
                modifier = Modifier.layoutId(QuizBottomBarLayoutId),
                isLastPage = pagerState.currentPage == totalPage - 1,
                onRightButtonClick = {
                    coroutineScope.launch {
                        val maximumPage = totalPage - 1
                        if (pagerState.currentPage == maximumPage) {
                            finishQuiz(pagerState.currentPage)
                        } else {
                            //onNextPage(pagerState.currentPage)
                            pagerState.moveNextPage(maximumPage)
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
    inputAnswer: (page: Int, inputAnswer: InputAnswer) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    HorizontalPager(
        modifier = modifier,
        pageCount = state.totalPage,
        state = pagerState,
    ) { pageIndex ->
        val focusRequester = remember(pagerState.currentPage) { FocusRequester() }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 24.dp),
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 16.dp))
            }
            questionSection(
                page = pageIndex,
                question = state.quizProblems[pageIndex].question,
            )
            val answer = state.quizProblems[pageIndex].answer
            answerSection(
                page = pageIndex,
                answer = when (answer) {
                    is Answer.Short -> Answer.Short(
                        state.quizProblems[pageIndex].correctAnswer
                            ?: duckieResponseFieldNpe("null 이 되면 안됩니다."),
                    )

                    is Answer.Choice, is Answer.ImageChoice -> answer
                    else -> duckieResponseFieldNpe("해당 분기로 빠질 수 없는 AnswerType 입니다.")
                },
                shortAnswerText = state.inputAnswers[pageIndex].answer,
                inputAnswers = state.inputAnswers,
                onAnswerChanged = inputAnswer,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
            )
        }
    }
}
