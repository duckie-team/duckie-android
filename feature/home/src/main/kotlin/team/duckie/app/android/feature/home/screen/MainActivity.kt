/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import team.duckie.app.android.common.android.deeplink.DynamicLinkHelper
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import team.duckie.app.android.common.compose.ui.dialog.ReportDialog
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.viewmodel.MainSideEffect
import team.duckie.app.android.feature.home.viewmodel.MainViewModel
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel
import team.duckie.app.android.feature.home.viewmodel.mypage.MyPageViewModel
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.feature.search.screen.SearchActivity
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.friend.FriendNavigator
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileEditNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import team.duckie.app.android.navigator.feature.profile.ViewAllNavigator
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import team.duckie.app.android.navigator.feature.tagedit.TagEditNavigator
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AllowMagicNumber("앱 종료 시간에 대해서 매직 넘버 처리")
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var waitTime = 2000L
    private val mainViewModel: MainViewModel by viewModels()
    private val rankingViewModel: RankingViewModel by viewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

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
    lateinit var profileNavigator: ProfileNavigator

    @Inject
    lateinit var friendsNavigator: FriendNavigator

    @Inject
    lateinit var profileEditNavigator: ProfileEditNavigator

    @Inject
    lateinit var viewAllNavigator: ViewAllNavigator

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startedGuide(intent)

        setContent {
            val state = mainViewModel.container.stateFlow.collectAsState().value

            LaunchedEffect(key1 = mainViewModel) {
                mainViewModel.container.sideEffectFlow
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            BackHandler {
                if (System.currentTimeMillis() - waitTime >= 1500L) {
                    waitTime = System.currentTimeMillis()
                    Toast.makeText(this, getString(R.string.app_exit_toast), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    finish()
                }
            }

            QuackTheme {
                ReportDialog(
                    visible = state.reportDialogVisible,
                    onClick = { mainViewModel.updateReportDialogVisible(false) },
                    onDismissRequest = { mainViewModel.updateReportDialogVisible(false) },
                )

                MainScreen(
                    mainViewModel = mainViewModel,
                    rankingViewModel = rankingViewModel,
                    myPageViewModel = myPageViewModel,
                    homeViewModel = homeViewModel,
                    state = state,
                    navigateToUserProfile = {
                        profileNavigator.navigateFrom(
                            activity = this,
                            intentBuilder = {
                                putExtra(Extras.UserId, it)
                            },
                        )
                    },
                    navigateToEditProfile = {
                        profileEditNavigator.navigateFrom(
                            activity = this,
                            intentBuilder = {
                                putExtra(Extras.UserId, it)
                            },
                        )
                    },
                    navigateToTagEdit = {
                        tagEditNavigator.navigateFrom(
                            activity = this,
                            intentBuilder = {
                                putExtra(Extras.UserId, it)
                            },
                        )
                    },
                    navigateToViewAll = { userId, examType ->
                        viewAllNavigator.navigateFrom(
                            activity = this,
                            intentBuilder = {
                                putExtra(Extras.UserId, userId)
                                putExtra(Extras.ExamType, examType)
                            },
                        )
                    },
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        myPageViewModel.getUserProfile()
    }

    private fun startedGuide(intent: Intent) {
        intent.getBooleanExtra(Extras.StartGuide, false).also { start ->
            if (start) {
                mainViewModel.updateGuideVisible(visible = true)
                intent.removeExtra(Extras.StartGuide)
            }
        }
    }

    private fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            is MainSideEffect.NavigateToSearch -> {
                startActivityWithAnimation<SearchActivity>(
                    intentBuilder = {
                        putExtra(Extras.SearchTag, sideEffect.searchTag)
                    },
                )
            }

            is MainSideEffect.NavigateToMainDetail -> {
                detailNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                    },
                )
            }

            is MainSideEffect.NavigateToCreateProblem -> {
                createProblemNavigator.navigateFrom(activity = this)
            }

            is MainSideEffect.NavigateToSetting -> {
                settingNavigator.navigateFrom(this)
            }

            is MainSideEffect.NavigateToNotification -> {
                notificationNavigator.navigateFrom(activity = this)
            }

            MainSideEffect.ClickRankingRetry -> {
                rankingViewModel.clickRetryRanking()
            }

            is MainSideEffect.NavigateToFriends -> {
                friendsNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.FriendType, sideEffect.friendType.index)
                        putExtra(Extras.UserId, sideEffect.myUserId)
                        putExtra(Extras.ProfileNickName, sideEffect.nickname)
                    },
                )
            }

            is MainSideEffect.SendToast -> {
                Toast.makeText(this, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is MainSideEffect.CopyExamIdDynamicLink -> {
                DynamicLinkHelper.createAndShareLink(this, sideEffect.examId)
            }

            is MainSideEffect.NavigateToProfile -> {
                profileNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }
        }
    }
}
