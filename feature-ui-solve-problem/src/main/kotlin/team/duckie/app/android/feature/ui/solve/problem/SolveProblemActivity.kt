/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalFoundationApi::class,
)

package team.duckie.app.android.feature.ui.solve.problem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
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
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
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

                Crossfade(targetState = state.isProblemsLoading) { isLoading ->
                    when (isLoading) {
                        true -> {
                            LoadingIndicator()
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
        }
    }
}
