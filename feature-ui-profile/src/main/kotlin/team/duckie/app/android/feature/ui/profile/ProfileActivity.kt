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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import team.duckie.app.android.navigator.feature.profile.ProfileEditNavigator
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import team.duckie.app.android.navigator.feature.tagedit.TagEditNavigator
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.dialog.ReportAlreadyExists
import team.duckie.app.android.util.compose.LaunchOnLifecycle
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.kotlin.AllowCyclomaticComplexMethod
import team.duckie.app.android.util.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
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
    lateinit var tagEditNavigator: TagEditNavigator

    @Inject
    lateinit var settingNavigator: SettingNavigator

    @Inject
    lateinit var searchNavigator: SearchNavigator

    @Inject
    lateinit var profileEditNavigator: ProfileEditNavigator

    @Inject
    lateinit var friendsNavigator: FriendNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO(riflockle7): 왜 이걸 직접 명시해 주어야 시스템 패딩이 정상적으로 적용되는지 모르겠음... 추후 확인 필요
            systemBarPaddings
            val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
            QuackTheme {
                when {
                    state.isError -> {
                        ErrorScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = QuackColor.White.composeColor)
                                .statusBarsPadding(),
                            onRetryClick = { viewModel.init() },
                        )
                    }

                    else -> {
                        when (state.isMe) {
                            true -> {
                                LaunchOnLifecycle {
                                    viewModel.getUserProfile()
                                }
                                MyProfileScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = QuackColor.White.composeColor)
                                        .systemBarsPadding(),
                                    userProfile = state.userProfile,
                                    isLoading = state.isLoading,
                                    onClickSetting = viewModel::clickSetting,
                                    onClickNotification = viewModel::clickNotification,
                                    onClickEditProfile = viewModel::clickEditProfile,
                                    onClickEditTag = viewModel::clickEditTag,
                                    onClickExam = viewModel::clickExam,
                                    onClickMakeExam = viewModel::clickMakeExam,
                                    onClickTag = viewModel::onClickTag,
                                    onClickFriend = viewModel::navigateFriends,
                                )
                            }

                            false -> {
                                OtherProfileScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = QuackColor.White.composeColor)
                                        .systemBarsPadding(),
                                    viewModel = viewModel,
                                )
                            }
                        }
                    }
                }
            }
        }
        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    @AllowCyclomaticComplexMethod
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

            is ProfileSideEffect.NavigateToTagEdit -> {
                tagEditNavigator.navigateFrom(
                    activity = this@ProfileActivity,
                    intentBuilder = {
                        putExtra(Extras.UserId, sideEffect.userId)
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
                        putExtra(Extras.ProfileNickName, sideEffect.nickname)
                    },
                )
            }

            is ProfileSideEffect.NavigateToEditProfile -> {
                profileEditNavigator.navigateFrom(
                    activity = this@ProfileActivity,
                    intentBuilder = { putExtra(Extras.UserId, sideEffect.userId) },
                )
            }
        }
    }
}
