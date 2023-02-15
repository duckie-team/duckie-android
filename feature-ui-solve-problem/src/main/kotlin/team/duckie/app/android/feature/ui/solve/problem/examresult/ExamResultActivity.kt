/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.examresult

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.solve.problem.examresult.screen.ExamResultScreen
import team.duckie.app.android.feature.ui.solve.problem.examresult.viewmodel.ExamResultSideEffect
import team.duckie.app.android.feature.ui.solve.problem.examresult.viewmodel.ExamResultViewModel
import team.duckie.app.android.util.compose.ToastWrapper
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.exception.handling.reporter.reportToToast
import team.duckie.app.android.navigator.feature.home.HomeNavigator
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class ExamResultActivity : BaseActivity() {
    private val viewModel: ExamResultViewModel by viewModels()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackHandler {
                startActivity(homeNavigator.intent(this))
            }

            QuackTheme {
                ExamResultScreen()
            }

            viewModel.observe(
                lifecycleOwner = this,
                sideEffect = ::handleSideEffect,
            )
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
                startActivity(homeNavigator.intent(this))
            }

            is ExamResultSideEffect.SendToast -> {
                ToastWrapper(this).invoke(message = sideEffect.message)
            }
        }
    }
}
