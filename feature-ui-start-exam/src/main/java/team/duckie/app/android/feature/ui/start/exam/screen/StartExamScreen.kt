/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.start.exam.screen.exam.StartExamInputScreen
import team.duckie.app.android.feature.ui.start.exam.screen.quiz.StartQuizInputScreen
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamState
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamViewModel
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackTitle1

@Composable
internal fun StartExamScreen(
    modifier: Modifier,
    viewModel: StartExamViewModel = activityViewModel(),
) = when (val state = viewModel.collectAsState().value) {
    is StartExamState.Loading -> StartExamLoadingScreen(modifier, viewModel)
    is StartExamState.Input -> {
        if (state.isQuiz) {
            StartQuizInputScreen(modifier, viewModel)
        } else {
            StartExamInputScreen(modifier, viewModel)
        }
    }

    is StartExamState.Error -> StartExamErrorScreen(modifier, viewModel)
}

/**
 * 시험 시작 Loading 화면
 * @see [StartExamState.Loading]
 */
@Composable
private fun StartExamLoadingScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        CircularProgressIndicator(
            color = QuackColor.DuckieOrange.composeColor,
        )
    }
}

/**
 * 시험 시작 에러 화면
 * @see [StartExamState.Error]
 */
@Composable
private fun StartExamErrorScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        QuackTitle1(
            text = "에러입니다\nTODO$viewModel\n추후 데이터 다시 가져오기 로직 넣어야 합니다.",
        )
    }
}
