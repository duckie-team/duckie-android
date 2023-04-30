/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.solve.problem.common.LoadingIndicator
import team.duckie.app.android.feature.ui.solve.problem.screen.SolveProblemScreen
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.navigator.feature.examresult.ExamResultNavigator
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
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
                val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    viewModel.initState()
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
                            SolveProblemScreen()
                        }
                    }
                }
            }
        }

        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: SolveProblemSideEffect) {
        when (sideEffect) {
            is SolveProblemSideEffect.FinishSolveProblem -> {
                examResultNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                        putExtra(Extras.Submitted, sideEffect.answers.toTypedArray())
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
        }
    }
}
