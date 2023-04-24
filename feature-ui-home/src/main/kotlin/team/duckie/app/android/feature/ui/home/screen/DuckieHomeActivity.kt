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
import team.duckie.app.android.feature.ui.home.viewmodel.mypage.MyPageViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.DuckieHomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.DuckieHomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.ranking.RankingViewModel
import team.duckie.app.android.feature.ui.search.screen.SearchActivity
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.friend.FriendNavigator
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileEditNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
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
class DuckieHomeActivity : BaseActivity() {

    private var waitTime = 2000L
    private val duckieHomeViewModel: DuckieHomeViewModel by viewModels()
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
    lateinit var profileNavigator: ProfileNavigator

    @Inject
    lateinit var friendsNavigator: FriendNavigator

    @Inject
    lateinit var profileEditNavigator: ProfileEditNavigator

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startedGuide(intent)

        setContent {
            val state by duckieHomeViewModel.collectAsState()

            LaunchedEffect(key1 = duckieHomeViewModel) {
                duckieHomeViewModel.container.sideEffectFlow
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
                    duckieHomeViewModel = duckieHomeViewModel,
                    rankingViewModel = rankingViewModel,
                    myPageViewModel = myPageViewModel,
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
                )
            }
        }
    }

    private fun startedGuide(intent: Intent) {
        intent.getBooleanExtra(Extras.StartGuide, false).also { start ->
            if (start) {
                duckieHomeViewModel.updateGuideVisible(visible = true)
                intent.removeExtra(Extras.StartGuide)
            }
        }
    }

    private fun handleSideEffect(sideEffect: DuckieHomeSideEffect) {
        when (sideEffect) {
            is DuckieHomeSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            is DuckieHomeSideEffect.NavigateToSearch -> {
                startActivityWithAnimation<SearchActivity>(
                    intentBuilder = {
                        putExtra(Extras.SearchTag, sideEffect.searchTag)
                    },
                )
            }

            is DuckieHomeSideEffect.NavigateToDuckieHomeDetail -> {
                detailNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                    },
                )
            }

            is DuckieHomeSideEffect.NavigateToCreateProblem -> {
                createProblemNavigator.navigateFrom(activity = this)
            }

            is DuckieHomeSideEffect.NavigateToSetting -> {
                settingNavigator.navigateFrom(this)
            }

            is DuckieHomeSideEffect.NavigateToNotification -> {
                notificationNavigator.navigateFrom(activity = this)
            }

            DuckieHomeSideEffect.ClickRankingRetry -> {
                rankingViewModel.clickRetryRanking()
            }

            is DuckieHomeSideEffect.NavigateToFriends -> {
                friendsNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.FriendType, sideEffect.friendType.index)
                        putExtra(Extras.UserId, sideEffect.myUserId)
                    },
                )
            }
        }
    }
}
