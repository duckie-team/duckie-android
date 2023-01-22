/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamSideEffect
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
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
                    // TODO(riflockle7): 시험 풀기 화면으로 이동 필요 및 필요 extra 확인 필요
                    finishWithAnimation()
                } else {
                    finishWithAnimation()
                }
            }
        }
    }
}
