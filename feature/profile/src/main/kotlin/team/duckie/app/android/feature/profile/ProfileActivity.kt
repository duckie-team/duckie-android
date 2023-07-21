/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.compose.LaunchOnLifecycle
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.dialog.ReportAlreadyExists
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.common.kotlin.AllowCyclomaticComplexMethod
import team.duckie.app.android.common.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.feature.profile.screen.MyProfileScreen
import team.duckie.app.android.feature.profile.screen.OtherProfileScreen
import team.duckie.app.android.feature.profile.viewmodel.ProfileViewModel
import team.duckie.app.android.feature.profile.viewmodel.sideeffect.ProfileSideEffect
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileStep
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.friend.FriendNavigator
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileEditNavigator
import team.duckie.app.android.navigator.feature.profile.ViewAllNavigator
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import team.duckie.app.android.navigator.feature.tagedit.TagEditNavigator
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
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

    @Inject
    lateinit var viewAllNavigator: ViewAllNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackHandler {
                viewModel.clickBackPress()
            }
            // TODO(riflockle7): 왜 이걸 직접 명시해 주어야 시스템 패딩이 정상적으로 적용되는지 모르겠음... 추후 확인 필요
            systemBarPaddings
            val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

            BackHandler(
                onBack = ::finishWithAnimation,
            )

            QuackTheme {
                QuackCrossfade(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.value)
                        .systemBarsPadding(),
                    targetState = state.step,
                ) { step ->
                    when (step) {
                        ProfileStep.Error -> {
                            ErrorScreen(
                                Modifier.fillMaxSize(),
                                onRetryClick = viewModel::init,
                            )
                        }

                        ProfileStep.Profile -> {
                            when (state.isMe) {
                                true -> {
                                    LaunchOnLifecycle {
                                        viewModel.getUserProfile()
                                    }
                                    MyProfileScreen(
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
                                        isAccessedProfile = true,
                                        navigateBack = viewModel::clickBackPress,
                                        onClickShowAll = viewModel::clickViewAll,
                                    )
                                }

                                false -> {
                                    OtherProfileScreen(viewModel = viewModel)
                                }
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
            is ProfileSideEffect.NavigateToBack -> {
                val resultIntent = Intent().apply {
                    putExtra(Extras.FollowChangedStatus, sideEffect.isFollow.not())
                    putExtra(Extras.FollowChangedUserId, sideEffect.userId)
                }
                setResult(RESULT_OK, resultIntent)
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

            is ProfileSideEffect.NavigateToViewAll -> {
                viewAllNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamType, sideEffect.examType)
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }
        }
    }
}
