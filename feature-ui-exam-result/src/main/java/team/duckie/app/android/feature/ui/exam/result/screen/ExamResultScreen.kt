/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.exam.result.ExamResultActivity
import team.duckie.app.android.feature.ui.exam.result.R
import team.duckie.app.android.feature.ui.exam.result.common.LoadingIndicator
import team.duckie.app.android.feature.ui.exam.result.common.ResultBottomBar
import team.duckie.app.android.feature.ui.exam.result.screen.exam.ExamResultContent
import team.duckie.app.android.feature.ui.exam.result.screen.quiz.QuizResultContent
import team.duckie.app.android.feature.ui.exam.result.viewmodel.ExamResultState
import team.duckie.app.android.feature.ui.exam.result.viewmodel.ExamResultViewModel
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun ExamResultScreen(
    viewModel: ExamResultViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val activity = LocalContext.current as ExamResultActivity

    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            QuackTopAppBar(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 16.dp),
                leadingIcon = QuackIcon.Close,
                onLeadingIconClick = viewModel::exitExam,
            )
        },
        bottomBar = {
            ResultBottomBar(
                onClickRetryButton = {
                    viewModel.clickRetry(activity.getString(R.string.feature_prepare))
                },
                onClickExitButton = viewModel::exitExam,
            )
        },
    ) { padding ->
        QuackCrossfade(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            targetState = state,
        ) {
            when (it) {
                is ExamResultState.Loading -> {
                    LoadingIndicator()
                }

                is ExamResultState.Success -> {
                    if (it.isQuiz) {
                        QuizResultContent(
                            resultImageUrl = it.reportUrl,
                            correctProblemCount = 1,
                            score = 2,
                            time = 3,
                            mainTag = "메인",
                        )
                    } else {
                        ExamResultContent(
                            resultImageUrl = it.reportUrl,
                        )
                    }

                }

                is ExamResultState.Error -> {
                    ErrorScreen(
                        Modifier,
                        false,
                        onRetryClick = viewModel::initState,
                    )
                }
            }
        }
    }
}
