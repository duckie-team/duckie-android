/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.exception.handling.reporter.reportToToast
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.feature.exam.result.screen.ExamResultScreen
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultSideEffect
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultViewModel
import team.duckie.app.android.navigator.feature.startexam.StartExamNavigator
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class ExamResultActivity : BaseActivity() {
    private val viewModel: ExamResultViewModel by viewModels()

    @Inject
    lateinit var startExamNavigator: StartExamNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
        setContent {
            BackHandler {
                viewModel.exitExam()
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

            is ExamResultSideEffect.FinishExamResult -> {
                finishWithAnimation()
            }

            is ExamResultSideEffect.NavigateToStartExam -> {
                startExamNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                        putExtra(Extras.Timer, sideEffect.timer)
                        putExtra(Extras.RequirementQuestion, sideEffect.requirementQuestion)
                        putExtra(Extras.RequirementPlaceholder, sideEffect.requirementPlaceholder)
                        putExtra(Extras.IsQuiz, true)
                    },
                    withFinish = true,
                )
            }
            is ExamResultSideEffect.SendReactionSuccessToast -> {
                ToastWrapper(this).invoke(getString(R.string.exam_result_post_reaction_success))
            }

            is ExamResultSideEffect.SendDeleteCommentSuccessToast -> {
                // TODO(limsaehyun): 취소 할 수 있게 구현해야 함
                ToastWrapper(this).invoke(getString(R.string.exam_result_delete_comment_success))
            }

            is ExamResultSideEffect.SendErrorToast -> {
                ToastWrapper(this).invoke(sideEffect.message)
            }

            is ExamResultSideEffect.SendIgnoreUserToast -> {
                // TODO(limsaehyun): 취소 할 수 있게 구현해야 함
                ToastWrapper(this).invoke(getString(R.string.exam_result_ignore_user_success, sideEffect.nickname))
            }

            is ExamResultSideEffect.SendReportCommentSuccessToast -> {
                ToastWrapper(this).invoke(getString(R.string.exam_result_report_comment_success))
            }
        }
    }
}
