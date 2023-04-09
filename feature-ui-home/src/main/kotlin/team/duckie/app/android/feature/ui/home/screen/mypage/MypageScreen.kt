/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.mypage

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel.MyPageViewModel
import team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel.sideeffect.MyPageSideEffect
import team.duckie.app.android.feature.ui.profile.screen.MyProfileScreen
import team.duckie.app.android.feature.ui.profile.screen.edit.ProfileEditActivity
import team.duckie.app.android.shared.ui.compose.dialog.ReportAlreadyExists
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.app.android.util.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.util.ui.startActivityWithAnimation

@Composable
internal fun MyPageScreen(
    navigateToSetting: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToExam: (Int) -> Unit,
    navigateToCreateProblem: () -> Unit,
    navigateToSearch: (String) -> Unit,
    navigateToFriend: (FriendsType, Int) -> Unit,
    viewModel: MyPageViewModel,
) {
    val activity = LocalContext.current as Activity
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
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
            }
        }
    }

    with(state) {
        MyProfileScreen(
            userProfile = userProfile,
            isLoading = isLoading,
            onClickSetting = viewModel::clickSetting,
            onClickNotification = viewModel::clickNotification,
            // TODO(EvergreenTree97) 추후 sideEffect로 관리
            onClickEditProfile = { activity.startActivityWithAnimation<ProfileEditActivity>() },
            onClickEditTag = { viewModel.clickEditTag(context.getString(R.string.provide_after)) },
            onClickExam = viewModel::clickExam,
            onClickMakeExam = viewModel::clickMakeExam,
            onClickFavoriteTag = { viewModel.clickEditProfile(context.getString(R.string.provide_after)) },
            onClickTag = viewModel::onClickTag,
            onClickFriend = navigateToFriend,
        )
    }
}
