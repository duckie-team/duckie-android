/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.create.problem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.create.problem.screen.ExamInformationScreen
import team.duckie.app.android.feature.ui.create.problem.screen.FindExamAreaScreen
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import javax.inject.Inject

@AndroidEntryPoint
class CreateProblemActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: CreateProblemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalViewModel provides viewModel) {
                val createProblemStep = viewModel.state.collectAsStateWithLifecycle().value.createProblemStep

                QuackAnimatedContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor),
                    targetState = createProblemStep,
                ) { step: CreateProblemStep ->
                    when (step) {
                        CreateProblemStep.ExamInformation -> ExamInformationScreen()
                        CreateProblemStep.FindExamArea -> FindExamAreaScreen()
                        CreateProblemStep.CreateProblem -> {}
                        CreateProblemStep.AdditionalInformation -> {}
                    }
                }
            }
        }
    }
}
