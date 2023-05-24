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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Problem.Companion.isSubjective
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.app.android.feature.solve.problem.answer.answerSection
import team.duckie.app.android.feature.solve.problem.common.ButtonBottomBar
import team.duckie.app.android.feature.solve.problem.common.TimerTopBar
import team.duckie.app.android.feature.solve.problem.question.questionSection
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.feature.solve.problem.viewmodel.state.SolveProblemState
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.isCurrentPage
import team.duckie.app.android.common.compose.rememberKeyboardVisible
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.answer.shortanswer.ShortAnswerForm
import team.duckie.app.android.feature.solve.problem.question.audio.AudioPlayer
import team.duckie.app.android.feature.solve.problem.question.video.VideoPlayer
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage

private const val QuizTopAppBarLayoutId = "QuizTopAppBar"
private const val QuizContentLayoutId = "QuizContent"
private const val QuizBottomBarLayoutId = "QuizBottomBar"

@Composable
internal fun QuizScreen(
    state: SolveProblemState,
    pagerState: PagerState,
    progress: () -> Float,
    stopExam: () -> Unit,
    finishQuiz: (Int, Boolean) -> Unit,
    onNextPage: (Int, InputAnswer, Int) -> Unit,
    startTimer: () -> Unit,
) {
    val totalPage = remember { state.totalPage }
    val coroutineScope = rememberCoroutineScope()
    var examExitDialogVisible by remember { mutableStateOf(false) }

    val timeOver by remember {
        derivedStateOf { progress() == 0f }
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
        startTimer()
    }

    LaunchedEffect(timeOver) {
        if (timeOver) {
            finishQuiz(pagerState.currentPage, false)
        }
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
                onRightButtonClick = {
                    coroutineScope.launch {
                        val maximumPage = totalPage - 1
                        if (pagerState.currentPage == maximumPage) {
                            finishQuiz(pagerState.currentPage, true)
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

    HorizontalPager(
        modifier = modifier,
        pageCount = state.totalPage,
        state = pagerState,
        userScrollEnabled = false,
    ) { pageIndex ->
        val problem = state.quizProblems[pageIndex]

        val focusRequester = remember { FocusRequester() }
        val requestFocus by remember { derivedStateOf { pagerState.isCurrentPage(pageIndex) } }

        LaunchedEffect(key1 = requestFocus) {
            if (requestFocus) {
                if (problem.isSubjective()) {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                } else {
                    keyboardController?.hide()
                }
            }
        }

        when {
            problem.isSubjective() -> FlexibleSubjectiveQuestionSection( // for keyboard flexible image height
                problem = problem,
                pageIndex = pageIndex,
                inputAnswers = inputAnswers,
                updateInputAnswers = updateInputAnswers,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
            )
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                }
                questionSection(
                    page = pageIndex,
                    question = problem.question,
                )
                val answer = problem.answer
                answerSection(
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
                    focusRequester = focusRequester,
                    keyboardController = keyboardController,
                )
            }
        }
    }
}

/**
 * TODO(limsaehyun)
 * keyboard가 올라왔을 때 남는 사이즈를 계산해서 question이 가려지지 않도록 사이즈를 유연하게 적용 해야함
 * 하지만 Keyboard Height 와 이에 맞게 이미지 사이즈를 계산하는 방법을 찾지 못함
 * 따라서 키보드가 올라왔을 때 weight를 적용하여 imePadding 을 적용한 부모 컴포넌트에 사이즈를 채우는 방식을 사용함
 */
@Composable
fun FlexibleSubjectiveQuestionSection(
    problem: Problem,
    pageIndex: Int,
    inputAnswers: ImmutableList<InputAnswer>,
    updateInputAnswers: (page: Int, inputAnswer: InputAnswer) -> Unit,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
) {
    val question = problem.question
    val keyboardVisible = rememberKeyboardVisible()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        Spacer(space = 16.dp)
        QuackHeadLine2(
            text = "${pageIndex + 1}. ${question.text}",
            padding = PaddingValues(horizontal = 16.dp),
        )
        Spacer(space = 12.dp)
        when (question) {
            is Question.Text -> {}
            is Question.Image -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = QuackColor.Black.composeColor,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .composed {
                            runIf(keyboardVisible.value) {
                                weight(1f)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    QuackImage(
                        modifier = Modifier,
                        src = question.imageUrl,
                        padding = PaddingValues(horizontal = 16.dp),
                    )
                }
            }

            is Question.Audio -> {
                AudioPlayer(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    url = question.audioUrl,
                )
            }

            is Question.Video -> {
                VideoPlayer(url = question.videoUrl)
            }
        }
        Spacer(space = 24.dp)
        ShortAnswerForm(
            modifier = Modifier.padding(horizontal = 16.dp),
            answer = problem.correctAnswer ?: "",
            text = inputAnswers[pageIndex].answer,
            onTextChanged = { inputText ->
                updateInputAnswers(pageIndex, InputAnswer(0, inputText))
            },
            onDone = { inputText ->
                updateInputAnswers(pageIndex, InputAnswer(0, inputText))
            },
            focusRequester = focusRequester,
            keyboardController = keyboardController,
        )
    }
}
