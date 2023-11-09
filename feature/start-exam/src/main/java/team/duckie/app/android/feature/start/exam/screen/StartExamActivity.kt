/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.start.exam.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.compose.util.addFocusCleaner
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamSideEffect
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamViewModel
import team.duckie.app.android.navigator.feature.solveproblem.SolveProblemNavigator
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class StartExamActivity : BaseActivity() {
    private val viewModel: StartExamViewModel by viewModels()

    @Inject
    lateinit var solveProblemNavigator: SolveProblemNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                StartExamScreen(
                    Modifier
                        .fillMaxSize()
                        .addFocusCleaner()
                        .background(color = QuackColor.White.value)
                        .systemBarsPadding(),
                )
            }
        }

        viewModel.observe(
            lifecycleOwner = this@StartExamActivity,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: StartExamSideEffect) {
        when (sideEffect) {
            is StartExamSideEffect.NavigateToSolveProblem -> {
                if (sideEffect.certified) {
                    solveProblemNavigator.navigateFrom(
                        activity = this,
                        intentBuilder = {
                            putExtra(Extras.ExamId, sideEffect.examId)
                            putExtra(Extras.ExamType, sideEffect.examType)
                            putExtra(Extras.RequirementAnswer, sideEffect.requirementAnswer)
                        },
                        withFinish = true,
                    )
                } else {
                    finishWithAnimation()
                }
            }

            StartExamSideEffect.FinishStartExam -> {
                finishWithAnimation()
            }

            is StartExamSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
                // TODO(EvergreenTree97): 에러 처리 관련 사항 결정되면 토스트 뿌려줘야함
            }
        }
    }
}
