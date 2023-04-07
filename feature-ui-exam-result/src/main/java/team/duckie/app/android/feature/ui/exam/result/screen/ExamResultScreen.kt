/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.exam.result.ExamResultActivity
import team.duckie.app.android.feature.ui.exam.result.R
import team.duckie.app.android.feature.ui.exam.result.viewmodel.ExamResultViewModel
import team.duckie.app.android.shared.ui.compose.quack.QuackCrossfade
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackSurface
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                GrayBorderSmallButton(
                    modifier = Modifier
                        .heightIn(min = 44.dp)
                        .weight(1f),
                    text = stringResource(id = R.string.solve_retry),
                    onClick = { // TODO(EvergreenTree97) 해당 시험 본 적 있는지 플래그 생기면 enabled 설정 해야함
                        viewModel.clickRetry(activity.getString(R.string.feature_prepare))
                    },
                )
                QuackSmallButton(
                    modifier = Modifier
                        .heightIn(44.dp)
                        .weight(1f),
                    type = QuackSmallButtonType.Fill,
                    text = stringResource(id = R.string.exit_exam),
                    enabled = true,
                    onClick = viewModel::exitExam,
                )
            }
        },
    ) { padding ->
        QuackCrossfade(targetState = state.isReportLoading) {
            when (it) {
                true -> {
                    LoadingIndicator()
                }

                false -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(all = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        QuackImage(
                            modifier = Modifier.fillMaxSize(),
                            src = state.reportUrl,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GrayBorderSmallButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier,
        backgroundColor = QuackColor.White,
        border = QuackBorder(color = QuackColor.Gray3),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackSubtitle(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 12.dp,
            ),
            text = text,
            singleLine = true,
        )
    }
}

// TODO(EvergreenTree97): QuackLoadingIndicator로 통합 필요
@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = QuackColor.DuckieOrange.composeColor)
    }
}
