/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.home.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableType
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.feature.home.constants.HomeStep
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.viewmodel.home.HomeSideEffect
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel

private val HomeHorizontalPadding = PaddingValues(horizontal = 16.dp)

@Composable
internal fun HomeScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    vm: HomeViewModel,
    setTargetExamId: (Int) -> Unit,
    onReport: () -> Unit,
    onShare: () -> Unit,
    navigateToCreateExam: () -> Unit,
    navigateToHomeDetail: (Int) -> Unit,
    navigateToSearch: (String) -> Unit,
    navigateToProfile: (Int) -> Unit,
) {
    val state = vm.collectAsState().value
    val bottomSheetDialogState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    // handle HomeSideEffect
    LaunchedEffect(Unit) {
        vm.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.ReportError -> {
                    with(sideEffect.exception) {
                        printStackTrace()
                        reportToCrashlyticsIfNeeded()
                    }
                }
            }
        }
    }

    DuckieSelectableBottomSheetDialog(
        modifier = Modifier.fillMaxSize(),
        bottomSheetState = bottomSheetDialogState,
        navigationBarsPaddingVisible = false,
        closeSheet = {
            coroutineScope.launch {
                bottomSheetDialogState.hide()
            }
        },
        onReport = onReport,
        onCopyLink = onShare,
        types = immutableListOf(DuckieSelectableType.CopyLink, DuckieSelectableType.Report),
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            QuackCrossfade(
                targetState = state.homeSelectedIndex,
            ) { page ->
                when {
                    state.isError -> ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRetryClick = vm::fetchJumbotrons,
                    )

                    page == HomeStep.HomeRecommendScreen -> HomeRecommendScreen(
                        state = state,
                        homeViewModel = vm,
                        navigateToCreateExam = navigateToCreateExam,
                        navigateToHomeDetail = navigateToHomeDetail,
                        navigateToSearch = navigateToSearch,
                        openExamBottomSheet = { exam ->
                            setTargetExamId(exam)
                            coroutineScope.launch {
                                bottomSheetDialogState.show()
                            }
                        },
                    )

                    page == HomeStep.HomeProceedScreen -> HomeProceedScreen(
                        initState = initState,
                        state = state,
                        homeViewModel = vm,
                        navigateToCreateExam = navigateToCreateExam,
                        navigateToHomeDetail = navigateToHomeDetail,
                        navigateToSearch = navigateToSearch,
                        openExamBottomSheet = { exam ->
                            setTargetExamId(exam)
                            coroutineScope.launch {
                                bottomSheetDialogState.show()
                            }
                        },
                    )

                    page == HomeStep.HomeFollowingScreen -> if (state.isFollowingExist) {
                        HomeRecommendFollowingExamScreen(
                            initState = initState,
                            modifier = Modifier.padding(HomeHorizontalPadding),
                            state = state,
                            navigateToHomeDetail = navigateToHomeDetail,
                            navigateToCreateExam = navigateToCreateExam,
                            navigateToProfile = navigateToProfile,
                        )
                    } else {
                        HomeRecommendFollowingScreen(
                            initState = initState,
                            modifier = Modifier.padding(HomeHorizontalPadding),
                            navigateToCreateExam = navigateToCreateExam,
                        )
                    }
                }
            }
        }
    }
}
