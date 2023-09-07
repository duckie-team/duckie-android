/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.solve.problem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.compose.moveNextPage
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.common.compose.util.addFocusCleaner
import team.duckie.app.android.domain.quiz.usecase.SubmitQuizUseCase
import team.duckie.app.android.feature.solve.problem.common.LoadingIndicator
import team.duckie.app.android.feature.solve.problem.screen.QuizScreen
import team.duckie.app.android.feature.solve.problem.screen.SolveProblemScreen
import team.duckie.app.android.feature.solve.problem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.feature.solve.problem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.navigator.feature.examresult.ExamResultNavigator
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class SolveProblemActivity : BaseActivity() {

    private val viewModel: SolveProblemViewModel by viewModels()

    @Inject
    lateinit var examResultNavigator: ExamResultNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                val state by viewModel.collectAsState()
                val progress by viewModel.timerCount.collectAsStateWithLifecycle()
                val pagerState = rememberPagerState(
                    pageCount = { state.totalPage },
                )

                LaunchedEffect(viewModel.container.sideEffectFlow) {
                    viewModel.container.sideEffectFlow.collect { sideEffect ->
                        handleSideEffect(
                            sideEffect = sideEffect,
                            pagerState = pagerState,
                        )
                    }
                }

                QuackCrossfade(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.value)
                        .addFocusCleaner()
                        .systemBarsPadding()
                        .navigationBarsPadding()
                        .imePadding(),
                    targetState = state,
                ) {
                    when {
                        state.isProblemsLoading -> {
                            LoadingIndicator()
                        }

                        state.isError -> {
                            ErrorScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .statusBarsPadding(),
                                isNetworkError = false,
                                onRetryClick = viewModel::initState,
                            )
                        }

                        else -> {
                            when (state.isQuiz) {
                                true -> QuizScreen(
                                    state = state,
                                    pagerState = pagerState,
                                    progress = { (progress / state.time) },
                                    stopExam = viewModel::stopExam,
                                    finishQuiz = viewModel::finishQuiz,
                                    startTimer = viewModel::startTimer,
                                    onNextPage = viewModel::moveNextPage,
                                )

                                false -> SolveProblemScreen(
                                    state = state,
                                    pagerState = pagerState,
                                    stopExam = viewModel::stopExam,
                                    finishExam = viewModel::finishExam,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun handleSideEffect(
        sideEffect: SolveProblemSideEffect,
        pagerState: PagerState,
    ) {
        when (sideEffect) {
            is SolveProblemSideEffect.FinishSolveProblem -> {
                examResultNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                        putExtra(Extras.Submitted, sideEffect.answers.toTypedArray())
                        putExtra(Extras.IsQuiz, false)
                        putExtra(Extras.IsPassed, false)
                    },
                    withFinish = true,
                )
            }

            is SolveProblemSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            SolveProblemSideEffect.NavigatePreviousScreen -> {
                finishWithAnimation()
            }

            is SolveProblemSideEffect.FinishQuiz -> {
                examResultNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                        putExtra(
                            Extras.UpdateQuizParam,
                            SubmitQuizUseCase.Param(
                                correctProblemCount = sideEffect.correctProblemCount,
                                time = sideEffect.time,
                                problemId = sideEffect.problemId,
                                requirementAnswer = sideEffect.requirementAnswer,
                                wrongAnswer = sideEffect.inputCurrentAnswer,
                            ),
                        )
                        putExtra(Extras.IsQuiz, true)
                    },
                    withFinish = true,
                )
            }

            is SolveProblemSideEffect.MoveNextPage -> {
                pagerState.moveNextPage(sideEffect.maxPage)
            }
        }
    }
}
