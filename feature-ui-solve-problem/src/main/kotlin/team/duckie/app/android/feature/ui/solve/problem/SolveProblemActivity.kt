/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.solve.problem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.solve.problem.common.LoadingIndicator
import team.duckie.app.android.feature.ui.solve.problem.screen.SolveProblemScreen
import team.duckie.app.android.feature.ui.solve.problem.viewmodel.SolveProblemViewModel
import team.duckie.app.android.util.ui.BaseActivity
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
    }
}
