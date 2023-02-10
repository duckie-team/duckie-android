/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.solve.problem.solveproblem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.solve.problem.examresult.ExamResultActivity
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.common.LoadingIndicator
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.screen.SolveProblemScreen
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.sideeffect.SolveProblemSideEffect
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.startActivityWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class SolveProblemActivity : BaseActivity() {

    private val viewModel: SolveProblemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    viewModel.getProblems()
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
                startActivityWithAnimation<ExamResultActivity>(
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                        putExtra(Extras.Submitted, sideEffect.answers.toTypedArray())
                    },
                )
            }

            is SolveProblemSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }
        }
    }
}
