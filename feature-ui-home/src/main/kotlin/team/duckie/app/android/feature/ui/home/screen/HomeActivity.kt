/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.DuckTestBottomNavigation
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.screen.mypage.MyPageScreen
import team.duckie.app.android.feature.ui.home.screen.ranking.RankingScreen
import team.duckie.app.android.feature.ui.home.screen.ranking.viewmodel.RankingViewModel
import team.duckie.app.android.feature.ui.home.screen.search.SearchMainScreen
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.search.screen.SearchActivity
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.startActivityWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

private const val HomeCrossFacadeLayoutId = "HomeCrossFacade"
private const val HomeBottomNavigationDividerLayoutId = "HomeBottomNavigationDivider"
private const val HomeBottomNavigationViewLayoutId = "HomeBottomNavigation"

@AllowMagicNumber("앱 종료 시간에 대해서 매직 넘버 처리")
@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var waitTime = 2000L
    private val homeViewModel: HomeViewModel by viewModels()
    private val rankingViewModel: RankingViewModel by viewModels()

    @Inject
    lateinit var createProblemNavigator: CreateProblemNavigator

    @Inject
    lateinit var notificationNavigator: NotificationNavigator

    @Inject
    lateinit var detailNavigator: DetailNavigator

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                Layout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background)
                        .padding(systemBarPaddings),
                    content = {
                        // TODO(limsaehyun): 추후에 QuackCrossfade 로 교체 필요
                        Crossfade(
                            modifier = Modifier.layoutId(HomeCrossFacadeLayoutId),
                            targetState = state.bottomNavigationStep,
                        ) { page ->
                            when (page) {
                                BottomNavigationStep.HomeScreen -> DuckieHomeScreen()
                                BottomNavigationStep.SearchScreen -> SearchMainScreen()
                                BottomNavigationStep.RankingScreen -> RankingScreen(
                                    viewModel = rankingViewModel,
                                    navigateToCreateProblem = {
                                        createProblemNavigator.navigateFrom(activity = this)
                                    },
                                    navigateToDetail = { examId ->
                                        detailNavigator.navigateFrom(
                                            activity = this,
                                            intentBuilder = {
                                                putExtra(Extras.ExamId, examId)
                                            },
                                        )
                                    },
                                )

                                BottomNavigationStep.MyPageScreen -> MyPageScreen(
                                    navigateToMyPage = {
                                        notificationNavigator.navigateFrom(activity = this)
                                    },
                                )
                            }
                        }
                        // TODO(limsaehyun): 추후에 QuackDivider 로 교체 필요
                        Divider(
                            modifier = Modifier.layoutId(HomeBottomNavigationDividerLayoutId),
                            color = QuackColor.Gray3.composeColor,
                        )
                        DuckTestBottomNavigation(
                            modifier = Modifier.layoutId(HomeBottomNavigationViewLayoutId),
                            selectedIndex = state.bottomNavigationStep.index,
                            onClick = { index ->
                                homeViewModel.navigationPage(
                                    BottomNavigationStep.toStep(index),
                                )
                            },
                        )
                    },
                ) { measurables, constraints ->
                    val looseConstraints = constraints.asLoose()

                    val homeBottomNavigationDividerPlaceable =
                        measurables.fastFirstOrNull { measurable ->
                            measurable.layoutId == HomeBottomNavigationDividerLayoutId
                        }?.measure(looseConstraints) ?: npe()

                    val homeBottomNavigationPlaceable = measurables.fastFirstOrNull { measurable ->
                        measurable.layoutId == HomeBottomNavigationViewLayoutId
                    }?.measure(looseConstraints) ?: npe()

                    val homeBottomNavigationHeight = homeBottomNavigationPlaceable.height

                    val homeBottomNavigationDividerHeight =
                        homeBottomNavigationDividerPlaceable.height

                    val homeCrossFadeConstraints = constraints.copy(
                        minHeight = 0,
                        maxHeight = constraints.maxHeight - homeBottomNavigationHeight,
                    )
                    val homeCrossFacadePlaceable = measurables.fastFirstOrNull { measurable ->
                        measurable.layoutId == HomeCrossFacadeLayoutId
                    }?.measure(homeCrossFadeConstraints) ?: npe()

                    layout(
                        width = constraints.maxWidth,
                        height = constraints.maxHeight,
                    ) {
                        homeCrossFacadePlaceable.place(
                            x = 0,
                            y = 0,
                        )

                        homeBottomNavigationDividerPlaceable.place(
                            x = 0,
                            y = constraints.maxHeight - homeBottomNavigationHeight - homeBottomNavigationDividerHeight,
                        )

                        homeBottomNavigationPlaceable.place(
                            x = 0,
                            y = constraints.maxHeight - homeBottomNavigationHeight,
                        )
                    }
                }
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

            HomeSideEffect.ClickRankingRetry -> {
                rankingViewModel.clickRetryRanking()
            }
        }
    }
}
