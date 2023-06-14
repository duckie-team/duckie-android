/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.network.NetworkUtil
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.app.android.common.compose.ui.dialog.ReportDialog
import team.duckie.app.android.feature.detail.common.DetailBottomLayout
import team.duckie.app.android.feature.detail.common.TopAppCustomBar
import team.duckie.app.android.feature.detail.screen.exam.ExamDetailContentLayout
import team.duckie.app.android.feature.detail.screen.quiz.QuizDetailContentLayout
import team.duckie.app.android.feature.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor

@Composable
internal fun DetailScreen(
    modifier: Modifier,
    viewModel: DetailViewModel = activityViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    var isNetworkAvailable: Boolean by remember { mutableStateOf(false) }
    isNetworkAvailable = !NetworkUtil.isNetworkAvailable(context)
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    return when {
        isNetworkAvailable -> ErrorScreen(
            modifier,
            true,
            onRetryClick = viewModel::refresh,
        )

        state is DetailState.Loading -> LoadingScreen(modifier)

        state is DetailState.Success -> {
            ReportDialog(
                onClick = {
                    viewModel.updateReportDialogVisible(false)
                },
                visible = state.reportDialogVisible,
                onDismissRequest = {
                    viewModel.updateReportDialogVisible(false)
                },
            )
            DuckieSelectableBottomSheetDialog(
                bottomSheetState = bottomSheetState,
                closeSheet = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onReport = { viewModel.report(state.exam.id) },
            ) {
                ExamDetailScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                    openBottomSheet = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    state = state,
                )
            }
        }

        else -> ErrorScreen(
            modifier,
            false,
            onRetryClick = viewModel::refresh,
        )
    }
}

/** 데이터 성공적으로 받은[DetailState.Success] 상세 화면 */
@Composable
internal fun ExamDetailScreen(
    viewModel: DetailViewModel,
    modifier: Modifier,
    openBottomSheet: () -> Unit,
    state: DetailState.Success,
) {
    Layout(
        modifier = modifier.navigationBarsPadding(),
        content = {
            // 상단 탭바 Layout
            TopAppCustomBar(
                modifier = Modifier.layoutId(DetailScreenTopAppBarLayoutId),
                state = state,
                onTagClick = viewModel::goToSearch,
            )
            when (state.isQuiz) {
                true -> {
                    QuizDetailContentLayout(
                        modifier = Modifier.layoutId(DetailScreenContentLayoutId),
                        state = state,
                        tagItemClick = viewModel::goToSearch,
                        moreButtonClick = openBottomSheet,
                        followButtonClick = viewModel::followUser,
                        profileClick = viewModel::goToProfile,
                    )
                }

                false -> {
                    ExamDetailContentLayout(
                        modifier = Modifier.layoutId(DetailScreenContentLayoutId),
                        state = state,
                        tagItemClick = viewModel::goToSearch,
                        moreButtonClick = openBottomSheet,
                        followButtonClick = viewModel::followUser,
                        profileClick = viewModel::goToProfile,
                    )
                }
            }
            // 최하단 Layout
            DetailBottomLayout(
                modifier = Modifier
                    .layoutId(DetailScreenBottomBarLayoutId)
                    .background(color = QuackColor.White.value),
                state = state,
                onHeartClick = viewModel::heartExam,
                onChallengeClick = viewModel::startExam,
            )
        },
        measurePolicy = screenMeasurePolicy(
            topLayoutId = DetailScreenTopAppBarLayoutId,
            contentLayoutId = DetailScreenContentLayoutId,
            bottomLayoutId = DetailScreenBottomBarLayoutId,
        ),
    )
}
