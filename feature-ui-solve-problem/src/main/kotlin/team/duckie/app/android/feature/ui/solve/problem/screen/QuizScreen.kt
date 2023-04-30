/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.solve.problem.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.feature.ui.solve.problem.R
import team.duckie.app.android.feature.ui.solve.problem.answer.answerSection
import team.duckie.app.android.feature.ui.solve.problem.common.CloseAndPageTopBar
import team.duckie.app.android.feature.ui.solve.problem.common.DoubleButtonBottomBar
import team.duckie.app.android.feature.ui.solve.problem.question.questionSection
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.state.SolveProblemState
import team.duckie.app.android.shared.ui.compose.LinearProgressBar
import team.duckie.app.android.shared.ui.compose.dialog.DuckieDialog
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.moveNextPage
import team.duckie.app.android.util.compose.movePreviousPage
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

private const val QuizTopAppBarLayoutId = "QuizTopAppBar"
private const val QuizContentLayoutId = "QuizContent"
private const val QuizBottomBarLayoutId = "QuizBottomBar"

@Composable
internal fun QuizScreen(
    viewModel: SolveProblemViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val totalPage = remember { state.totalPage }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var examExitDialogVisible by remember { mutableStateOf(false) }
    var examSubmitDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.setPage(pagerState.currentPage)
    }

    // 시험 종료 다이얼로그
    DuckieDialog(
        title = stringResource(id = R.string.quit_exam),
        message = stringResource(id = R.string.not_saved),
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { examExitDialogVisible = false },
        rightButtonText = stringResource(id = R.string.quit),
        rightButtonOnClick = { viewModel.stopExam() },
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
        rightButtonOnClick = { viewModel.finishExam() },
        visible = examSubmitDialogVisible,
        onDismissRequest = { examSubmitDialogVisible = false },
    )

    Layout(
        content = {
            CloseAndPageTopBar(
                modifier = Modifier
                    .layoutId(QuizTopAppBarLayoutId)
                    .padding(vertical = 12.dp)
                    .padding(end = 16.dp),
                onCloseClick = {
                    examExitDialogVisible = true
                },
                currentPage = pagerState.currentPage + 1,
                totalPage = totalPage,
            )
            ContentSection(
                modifier = Modifier.layoutId(QuizContentLayoutId),
                viewModel = viewModel,
                pagerState = pagerState,
                state = state,
            )
            DoubleButtonBottomBar(
                modifier = Modifier.layoutId(QuizBottomBarLayoutId),
                isFirstPage = pagerState.currentPage == 0,
                isLastPage = pagerState.currentPage == totalPage - 1,
                onLeftButtonClick = {
                    coroutineScope.launch {
                        pagerState.movePreviousPage(viewModel::onMovePreviousPage)
                    }
                },
                onRightButtonClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage == totalPage - 1) {
                            examSubmitDialogVisible = true
                        } else {
                            pagerState.moveNextPage(viewModel::onMoveNextPage)
                        }
                    }
                },
            )
        },
        measurePolicy = screenMeasurePolicy(
            topLayoutId = QuizTopAppBarLayoutId,
            contentLayoutId = QuizContentLayoutId,
            bottomLayoutId = QuizBottomBarLayoutId,
        )
    )
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    viewModel: SolveProblemViewModel,
    pagerState: PagerState,
    state: SolveProblemState,
) {
    val progress by viewModel.timerCount.collectAsStateWithLifecycle()
    val timeOver by remember {
        derivedStateOf { progress == 0 }
    }
    LaunchedEffect(timeOver) {
        if(timeOver){
            Log.d("timeOver", "timeOver")
            // TODO(EvergreenTree97) 추후 덕퀴즈 종료로 이동 viewModel.finishExam()
        }
    }
    DisposableEffect(state.currentPageIndex) {
        viewModel.startTimer()
        onDispose {
            viewModel.stopTimer()
        }
    }
    HorizontalPager(
        modifier = modifier,
        pageCount = state.totalPage,
        state = pagerState,
    ) { pageIndex ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 24.dp),
        ) {
            progressSection(
                progress = { progress.toFloat() / 3 },
            )
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
            )
        }
    }
}

fun LazyListScope.progressSection(
    modifier: Modifier = Modifier,
    progress: () -> Float,
) {
    item {
        LinearProgressBar(
            modifier = modifier,
            progress = progress(),
        )
    }
}
