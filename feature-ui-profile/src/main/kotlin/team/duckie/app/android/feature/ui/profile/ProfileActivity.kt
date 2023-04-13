/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.profile.screen.MyProfileScreen
import team.duckie.app.android.feature.ui.profile.screen.OtherProfileScreen
import team.duckie.app.android.feature.ui.profile.viewmodel.ProfileViewModel
import team.duckie.app.android.feature.ui.profile.viewmodel.sideeffect.ProfileSideEffect
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.friend.FriendNavigator
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import team.duckie.app.android.shared.ui.compose.dialog.ReportAlreadyExists
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var createProblemNavigator: CreateProblemNavigator

    @Inject
    lateinit var notificationNavigator: NotificationNavigator

    @Inject
    lateinit var detailNavigator: DetailNavigator

    @Inject
    lateinit var settingNavigator: SettingNavigator

    @Inject
    lateinit var searchNavigator: SearchNavigator

    @Inject
    lateinit var friendsNavigator: FriendNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
            QuackTheme {
                LaunchedEffect(Unit) {
                    viewModel.init()
                }
                when (state.isMe) {
                    true -> {
                        MyProfileScreen(
                            userProfile = state.userProfile,
                            isLoading = state.isLoading,
                            onClickSetting = viewModel::clickSetting,
                            onClickNotification = viewModel::clickNotification,
                            onClickEditProfile = { viewModel.clickEditProfile(getString(R.string.provide_after)) },
                            onClickEditTag = { viewModel.clickEditTag(getString(R.string.provide_after)) },
                            onClickExam = viewModel::clickExam,
                            onClickMakeExam = viewModel::clickMakeExam,
                            onClickFavoriteTag = { viewModel.clickEditProfile(getString(R.string.provide_after)) },
                            onClickTag = viewModel::onClickTag,
                            onClickFriend = viewModel::navigateFriends,
                        )
                    }

                    false -> {
                        OtherProfileScreen(viewModel)
                    }
                }
            }
        }
        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: ProfileSideEffect) {
        when (sideEffect) {
            ProfileSideEffect.NavigateToBack -> {
                finishWithAnimation()
            }

            ProfileSideEffect.NavigateToNotification -> {
                notificationNavigator.navigateFrom(this@ProfileActivity)
            }

            ProfileSideEffect.NavigateToSetting -> {
                settingNavigator.navigateFrom(this@ProfileActivity)
            }

            ProfileSideEffect.NavigateToMakeExam -> {
                createProblemNavigator.navigateFrom(this@ProfileActivity)
            }

            is ProfileSideEffect.NavigateToExamDetail -> {
                detailNavigator.navigateFrom(
                    activity = this@ProfileActivity,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                    },
                )
            }

            is ProfileSideEffect.ReportError -> {
                with(sideEffect.exception) {
                    printStackTrace()
                    reportToCrashlyticsIfNeeded()
                    if (isReportAlreadyExists) {
                        ToastWrapper(this@ProfileActivity).invoke(ReportAlreadyExists)
                    }
                }
            }

            is ProfileSideEffect.SendToast -> {
                ToastWrapper(this@ProfileActivity).invoke(sideEffect.message)
            }

            ProfileSideEffect.NavigateToFollowPage -> {}

            is ProfileSideEffect.NavigateToSearch -> {
                searchNavigator.navigateFrom(
                    activity = this@ProfileActivity,
                    intentBuilder = { putExtra(Extras.SearchTag, sideEffect.tagName) },
                )
            }

            is ProfileSideEffect.NavigateToFriends -> {
                friendsNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.FriendType, sideEffect.friendType.index)
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }
        }
    }
}
