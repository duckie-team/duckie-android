/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.ui.solve.problem.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import team.duckie.app.android.feature.ui.solve.problem.common.CloseAndPageTopBar
import team.duckie.app.android.feature.ui.solve.problem.common.DoubleButtonBottomBar
import team.duckie.app.android.feature.ui.solve.problem.question.questionSection
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.SolveProblemState
import team.duckie.app.android.shared.ui.compose.dialog.DuckieDialog
import team.duckie.app.android.util.compose.moveNextPage
import team.duckie.app.android.util.compose.movePrevPage
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

private const val SolveProblemTopAppBarLayoutId = "SolveProblemTopAppBar"
private const val SolveProblemContentLayoutId = "SolveProblemContent"
private const val SolveProblemBottomBarLayoutId = "SolveProblemBottomBar"

@Composable
internal fun SolveProblemScreen(
    state: SolveProblemState,
    inputAnswer: (Int, InputAnswer) -> Unit,
    stopExam: () -> Unit,
    finishExam: () -> Unit,
) {
    val totalPage = remember { state.totalPage }
    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()
    var examExitDialogVisible by remember { mutableStateOf(false) }
    var examSubmitDialogVisible by remember { mutableStateOf(false) }

    // 시험 종료 다이얼로그
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

    // 답안 제출 다이얼로그
    DuckieDialog(
        title = stringResource(id = R.string.submit_answer),
        message = stringResource(id = R.string.submit_answer_warning),
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { examSubmitDialogVisible = false },
        rightButtonText = stringResource(id = R.string.submit),
        rightButtonOnClick = finishExam,
        visible = examSubmitDialogVisible,
        onDismissRequest = { examSubmitDialogVisible = false },
    )

    Layout(
        content = {
            CloseAndPageTopBar(
                modifier = Modifier
                    .layoutId(SolveProblemTopAppBarLayoutId)
                    .padding(vertical = 12.dp)
                    .padding(end = 16.dp),
                onCloseClick = {
                    examExitDialogVisible = true
                },
                currentPage = pagerState.currentPage + 1,
                totalPage = totalPage,
            )
            ContentSection(
                modifier = Modifier.layoutId(SolveProblemContentLayoutId),
                pagerState = pagerState,
                state = state,
                inputAnswer = inputAnswer,
            )
            DoubleButtonBottomBar(
                modifier = Modifier.layoutId(SolveProblemBottomBarLayoutId),
                isFirstPage = pagerState.currentPage == 0,
                isLastPage = pagerState.currentPage == totalPage - 1,
                onLeftButtonClick = {
                    coroutineScope.launch {
                        pagerState.movePrevPage()
                    }
                },
                onRightButtonClick = {
                    coroutineScope.launch {
                        val maximumPage = totalPage - 1

                        if (pagerState.currentPage == maximumPage) {
                            examSubmitDialogVisible = true
                        } else {
                            pagerState.moveNextPage(maximumPage)
                        }
                    }
                },
            )
        },
        measurePolicy = screenMeasurePolicy(
            topLayoutId = SolveProblemTopAppBarLayoutId,
            contentLayoutId = SolveProblemContentLayoutId,
            bottomLayoutId = SolveProblemBottomBarLayoutId,
        ),
    )
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    inputAnswer: (Int, InputAnswer) -> Unit,
    pagerState: PagerState,
    state: SolveProblemState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val currentProblem = state.problems[pagerState.currentPage].problem

    LaunchedEffect(pagerState.currentPageOffsetFraction) {
        if (currentProblem.answer?.isShortAnswer() == true && pagerState.currentPageOffsetFraction == 0f) {
            keyboardController?.show()
            focusRequester.requestFocus()
        } else {
            keyboardController?.hide()
        }
    }

    HorizontalPager(
        modifier = modifier,
        pageCount = state.totalPage,
        state = pagerState,
    ) { pageIndex ->
        val problem = state.problems[pageIndex].problem

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 24.dp),
        ) {
            questionSection(
                page = pageIndex,
                question = problem.question,
            )
            val answer = problem.answer
            answerSection(
                page = pageIndex,
                answer = when (answer) {
                    is Answer.Short -> Answer.Short(
                        problem.correctAnswer
                            ?: duckieResponseFieldNpe("null 이 되면 안됩니다."),
                    )

                    is Answer.Choice, is Answer.ImageChoice -> answer
                    else -> duckieResponseFieldNpe("해당 분기로 빠질 수 없는 AnswerType 입니다.")
                },
                inputAnswers = state.inputAnswers,
                onClickAnswer = inputAnswer,
                keyboardController = keyboardController,
                focusRequester = focusRequester,
            )
        }
    }
}
