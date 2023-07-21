/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.collectAndHandleState
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.feature.profile.screen.viewall.ViewAllScreen
import team.duckie.app.android.feature.profile.viewmodel.ViewAllViewModel
import team.duckie.app.android.feature.profile.viewmodel.sideeffect.ViewAllSideEffect
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.ViewAllState
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.quackquack.material.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class ViewAllActivity : BaseActivity() {

    @Inject
    lateinit var detailNavigator: DetailNavigator

    private val viewModel: ViewAllViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                val state by viewModel.collectAsState()
                when (state) {
                    is ViewAllState.Error -> ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRetryClick = viewModel::fetchExamItems,
                    )

                    ViewAllState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())

                    is ViewAllState.Success -> {
                        val successState = (state as ViewAllState.Success)
                        ViewAllScreen(
                            examType = successState.examType,
                            onBackPressed = viewModel::clickViewAllBackPress,
                            profileExams = when (successState.examType) {
                                ExamType.Heart -> viewModel.heartExams.collectAndHandleState(
                                    handleLoadStates = viewModel::handleLoadState,
                                )

                                else -> viewModel.submittedExams.collectAndHandleState(
                                    handleLoadStates = viewModel::handleLoadState,
                                )
                            },
                            profileExamInstances = viewModel.solvedExams.collectAndHandleState(
                                handleLoadStates = viewModel::handleLoadState,
                            ),
                            onItemClick = viewModel::clickExam,
                        )
                    }
                }
            }
        }
        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: ViewAllSideEffect) {
        when (sideEffect) {
            is ViewAllSideEffect.NavigateToExamDetail -> {
                detailNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                    },
                )
            }

            ViewAllSideEffect.NavigateToProfile -> {
                finish()
            }

            is ViewAllSideEffect.ReportError -> {
                with(sideEffect.exception) {
                    printStackTrace()
                    reportToCrashlyticsIfNeeded()
                }
            }
        }
    }
}
