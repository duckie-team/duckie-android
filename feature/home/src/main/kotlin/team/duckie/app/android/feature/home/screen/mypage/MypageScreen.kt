/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.home.screen.mypage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.dialog.ReportAlreadyExists
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.common.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.viewmodel.mypage.MyPageSideEffect
import team.duckie.app.android.feature.home.viewmodel.mypage.MyPageViewModel
import team.duckie.app.android.feature.profile.screen.MyProfileScreen
import team.duckie.app.android.feature.profile.screen.viewall.ViewAllScreen
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileStep
import team.duckie.quackquack.ui.color.QuackColor

const val CreateExamIsNotSupported = "Created exam is not supported"

@Composable
internal fun MyPageScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToExam: (Int) -> Unit,
    navigateToCreateProblem: () -> Unit,
    navigateToSearch: (String) -> Unit,
    navigateToFriend: (FriendsType, Int, String) -> Unit,
    navigateToEditProfile: (Int) -> Unit,
    navigateToTagEdit: (Int) -> Unit,
    viewModel: MyPageViewModel,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isPullRefresh by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        if (state.step is ProfileStep.ViewAll) {
            viewModel.clickViewAllBackPress()
        }
    }

    @AllowMagicNumber
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isPullRefresh,
        onRefresh = {
            isPullRefresh = true
            viewModel.getUserProfile()
            coroutineScope.launch {
                delay(1000)
                isPullRefresh = false
            }
        },
    )

    LaunchedEffect(Unit) {
        initState(MainScreenType.MyPage) { viewModel.getUserProfile() }
    }

    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                MyPageSideEffect.NavigateToNotification -> {
                    navigateToNotification()
                }

                MyPageSideEffect.NavigateToSetting -> {
                    navigateToSetting()
                }

                MyPageSideEffect.NavigateToMakeExam -> {
                    navigateToCreateProblem()
                }

                is MyPageSideEffect.NavigateToExamDetail -> {
                    navigateToExam(sideEffect.examId)
                }

                is MyPageSideEffect.ReportError -> {
                    with(sideEffect.exception) {
                        printStackTrace()
                        reportToCrashlyticsIfNeeded()
                        if (isReportAlreadyExists) {
                            ToastWrapper(context).invoke(ReportAlreadyExists)
                        }
                    }
                }

                is MyPageSideEffect.SendToast -> {
                    ToastWrapper(context).invoke(sideEffect.message)
                }

                is MyPageSideEffect.NavigateToSearch -> {
                    navigateToSearch(sideEffect.tagName)
                }

                is MyPageSideEffect.NavigateToEditProfile -> {
                    navigateToEditProfile(sideEffect.userId)
                }

                is MyPageSideEffect.NavigateToTagEdit -> {
                    navigateToTagEdit(sideEffect.userId)
                }
            }
        }
    }

    QuackCrossfade(targetState = state.step) { step ->
        when (step) {
            ProfileStep.Error -> ErrorScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                onRetryClick = viewModel::getUserProfile,
            )

            ProfileStep.Profile -> Box(Modifier.pullRefresh(pullRefreshState)) {
                MyProfileScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor),
                    userProfile = state.userProfile,
                    isLoading = state.isLoading,
                    onClickSetting = viewModel::clickSetting,
                    onClickNotification = viewModel::clickNotification,
                    onClickEditProfile = viewModel::clickEditProfile,
                    onClickEditTag = viewModel::clickEditTag,
                    onClickExam = viewModel::clickExam,
                    onClickMakeExam = viewModel::clickMakeExam,
                    onClickTag = viewModel::onClickTag,
                    onClickFriend = navigateToFriend,
                    onClickShowAll = viewModel::clickViewAll,
                )

                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = isPullRefresh,
                    state = pullRefreshState,
                )
            }

            is ProfileStep.ViewAll -> {
                ViewAllScreen(
                    examType = step.examType,
                    onBackPressed = viewModel::clickViewAllBackPress,
                    profileExams = when (step.examType) {
                        ExamType.Heart -> viewModel.heartExams.collectAsLazyPagingItems()
                        ExamType.Created -> viewModel.submittedExams.collectAsLazyPagingItems()
                        ExamType.Solved -> throw IllegalStateException(CreateExamIsNotSupported)
                    },
                    profileExamInstances = viewModel.solvedExams.collectAsLazyPagingItems(),
                    onItemClick = viewModel::clickExam,
                )
            }
        }
    }
}
