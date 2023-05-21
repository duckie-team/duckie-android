/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.exam.result.screen.ExamResultScreen
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultSideEffect
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultViewModel
import team.duckie.app.android.navigator.feature.home.HomeNavigator
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.exception.handling.reporter.reportToToast
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class ExamResultActivity : BaseActivity() {
    private val viewModel: ExamResultViewModel by viewModels()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
        setContent {
            BackHandler {
                homeNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                    },
                    withFinish = true,
                )
            }

            QuackTheme {
                ExamResultScreen()
            }
        }
    }

    private fun handleSideEffect(sideEffect: ExamResultSideEffect) {
        when (sideEffect) {
            is ExamResultSideEffect.ReportError -> {
                sideEffect.exception.run {
                    printStackTrace()
                    reportToToast()
                    reportToCrashlyticsIfNeeded()
                }
            }

            ExamResultSideEffect.FinishExamResult -> {
                homeNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                    },
                    withFinish = true,
                )
            }

            is ExamResultSideEffect.SendToast -> {
                ToastWrapper(this).invoke(message = sideEffect.message)
            }
        }
    }
}
