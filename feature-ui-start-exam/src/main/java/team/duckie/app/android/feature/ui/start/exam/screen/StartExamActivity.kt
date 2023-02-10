/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.screen

import android.os.Bundle
import android.util.Log
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
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.SolveProblemActivity
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamSideEffect
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.app.android.util.ui.startActivityWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class StartExamActivity : BaseActivity() {
    private val viewModel: StartExamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                StartExamScreen(
                    Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor)
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
            is StartExamSideEffect.FinishStartExam -> {
                if (sideEffect.certified) {
                    startActivityWithAnimation<SolveProblemActivity>(
                        intentBuilder = { putExtra(Extras.ExamId, sideEffect.examId) },
                    )
                } else {
                    Log.i("riflockle7", "화면 종료")
                    finishWithAnimation()
                }
            }

            is StartExamSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
                // TODO(EvergreenTree97): 에러 처리 관련 사항 결정되면 토스트 뿌려줘야함
            }
        }
    }
}
