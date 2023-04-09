/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel.MyPageViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.home.HomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.home.HomeSideEffect
import team.duckie.app.android.feature.ui.search.screen.SearchActivity
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.friend.FriendNavigator
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.startActivityWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AllowMagicNumber("앱 종료 시간에 대해서 매직 넘버 처리")
@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var waitTime = 2000L
    private val homeViewModel: HomeViewModel by viewModels()
    private val rankingViewModel: RankingViewModel by viewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()

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

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startedGuide(intent)

        setContent {
            val state by homeViewModel.collectAsState()

            LaunchedEffect(key1 = homeViewModel) {
                homeViewModel.container.sideEffectFlow
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
                DuckieHomeScreen(
                    homeViewModel = homeViewModel,
                    rankingViewModel = rankingViewModel,
                    myPageViewModel = myPageViewModel,
                    state = state,
                )
            }
        }
    }

    private fun startedGuide(intent: Intent) {
        intent.getBooleanExtra(Extras.StartGuide, false).also { start ->
            if (start) {
                homeViewModel.updateGuideVisible(visible = true)
                intent.removeExtra(Extras.StartGuide)
            }
        }
    }

    private fun handleSideEffect(sideEffect: HomeSideEffect) {
        when (sideEffect) {
            is HomeSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            is HomeSideEffect.NavigateToSearch -> {
                startActivityWithAnimation<SearchActivity>(
                    intentBuilder = {
                        putExtra(Extras.SearchTag, sideEffect.searchTag)
                    },
                )
            }

            is HomeSideEffect.NavigateToHomeDetail -> {
                detailNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                    },
                )
            }

            is HomeSideEffect.NavigateToCreateProblem -> {
                createProblemNavigator.navigateFrom(activity = this)
            }

            is HomeSideEffect.NavigateToSetting -> {
                settingNavigator.navigateFrom(this)
            }

            is HomeSideEffect.NavigateToNotification -> {
                notificationNavigator.navigateFrom(activity = this)
            }

            HomeSideEffect.ClickRankingRetry -> {
                rankingViewModel.clickRetryRanking()
            }

            is HomeSideEffect.NavigateToFriends -> {
                friendsNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = { putExtra(Extras.FriendType, sideEffect.friendType.index) },
                )
            }
        }
    }
}
