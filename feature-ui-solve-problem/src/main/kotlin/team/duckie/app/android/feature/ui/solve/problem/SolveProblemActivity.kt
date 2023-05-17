/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.solve.problem

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
import team.duckie.app.android.domain.quiz.usecase.UpdateQuizUseCase
import team.duckie.app.android.feature.ui.solve.problem.common.LoadingIndicator
import team.duckie.app.android.feature.ui.solve.problem.screen.QuizScreen
import team.duckie.app.android.feature.ui.solve.problem.screen.SolveProblemScreen
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.SolveProblemViewModel.Companion.TimerCount
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.navigator.feature.examresult.ExamResultNavigator
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.app.android.util.compose.moveNextPage
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme
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
                val pagerState = rememberPagerState()

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
                        .background(color = QuackColor.White.composeColor)
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
                                    progress = { (progress.toFloat() / TimerCount) },
                                    stopExam = viewModel::stopExam,
                                    finishQuiz = viewModel::finishQuiz,
                                    startTimer = viewModel::startTimer,
                                    onNextPage = viewModel::moveNextPage,
                                )

                                false -> SolveProblemScreen(
                                    state = state,
                                    pagerState = pagerState,
                                    inputAnswer = viewModel::inputAnswer,
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
                            Extras.UpdateQuizParam, UpdateQuizUseCase.Param(
                                correctProblemCount = sideEffect.correctProblemCount,
                                time = sideEffect.time,
                                problemId = sideEffect.problemId,
                            )
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
